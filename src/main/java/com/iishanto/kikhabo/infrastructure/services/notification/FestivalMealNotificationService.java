package com.iishanto.kikhabo.infrastructure.services.notification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iishanto.kikhabo.domain.entities.notification.NotificationVariant;
import com.iishanto.kikhabo.infrastructure.model.RecipeCacheEntity;
import com.iishanto.kikhabo.infrastructure.repositories.database.RecipeCacheRepository;
import com.iishanto.kikhabo.infrastructure.services.chatbot.ChatBotApiService;
import com.iishanto.kikhabo.infrastructure.services.recipe.RecipeImageService;
import com.iishanto.kikhabo.infrastructure.services.scheduler.CountryCodeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * For a given country, fetches today's public holidays from Calendarific,
 * then asks Gemini to suggest a culturally appropriate festival meal and
 * generate a push notification. The resulting recipe is cached in
 * {@code recipe_cache} (source = "festival-gemini").
 */
@Service
public class FestivalMealNotificationService {

    private static final Logger log = LoggerFactory.getLogger(FestivalMealNotificationService.class);
    private static final String CALENDARIFIC_URL = "https://calendarific.com/api/v2/holidays";

    private final RestClient restClient;
    private final ChatBotApiService chatBotApiService;
    private final RecipeCacheRepository recipeCacheRepository;
    private final RecipeImageService recipeImageService;
    private final ObjectMapper objectMapper;
    private final String calendarificKey;

    public FestivalMealNotificationService(RestClient restClient,
                                           ChatBotApiService chatBotApiService,
                                           RecipeCacheRepository recipeCacheRepository,
                                           RecipeImageService recipeImageService,
                                           ObjectMapper objectMapper,
                                           @Value("${calendarific.api.key}") String calendarificKey) {
        this.restClient = restClient;
        this.chatBotApiService = chatBotApiService;
        this.recipeCacheRepository = recipeCacheRepository;
        this.recipeImageService = recipeImageService;
        this.objectMapper = objectMapper;
        this.calendarificKey = calendarificKey;
    }

    /**
     * Returns a festival meal notification variant for the country if today
     * has a public holiday, otherwise returns empty.
     */
    public Optional<NotificationVariant> generateFestivalVariant(String country, String language) {
        String isoCode = CountryCodeHelper.getCode(country);
        if (isoCode == null) {
            log.warn("[FestivalMeal] No ISO code for country='{}', skipping", country);
            return Optional.empty();
        }

        JsonNode holiday = fetchTodayHoliday(isoCode, country);
        if (holiday == null) {
            log.info("[FestivalMeal] No holiday today for country='{}'", country);
            return Optional.empty();
        }

        String festivalName = holiday.path("name").asText("").trim();
        String festivalDesc = holiday.path("description").asText("").trim();
        String festivalType = extractType(holiday);
        String festivalDate = holiday.path("date").path("iso").asText(LocalDate.now().toString());
        log.info("[FestivalMeal] Holiday found for country='{}': '{}' ({}) on {}", country, festivalName, festivalType, festivalDate);

        // Dedup: one festival recipe per country per date
        Optional<RecipeCacheEntity> cached = recipeCacheRepository
                .findBySourceAndCountryIgnoreCaseAndDatePublished("festival-gemini", country, festivalDate);
        if (cached.isPresent()) {
            log.info("[FestivalMeal] Cache hit for festival='{}' country='{}'", festivalName, country);
            RecipeCacheEntity entity = cached.get();
            String extra = "{\"recipeId\":\"" + entity.getRecipeId() + "\"}";
            return Optional.of(NotificationVariant.builder()
                    .audience("general")
                    .title(fallbackTitle(festivalName, entity.getName()))
                    .body(fallbackBody(entity.getDescription()))
                    .extra(extra)
                    .imageUrl(entity.getImage())
                    .build());
        }

        // Fetch last 5 suggested English dish names for this country to avoid repetition
        List<String> recentDishes = recipeCacheRepository
                .findTop5BySourceAndCountryIgnoreCaseOrderByCachedAtDesc("festival-gemini", country)
                .stream()
                .map(RecipeCacheEntity::getMongoName)
                .filter(n -> n != null && !n.isBlank())
                .toList();

        return generateAndCache(country, language, festivalName, festivalDesc, festivalType, festivalDate, recentDishes);
    }

    // ── Calendarific ─────────────────────────────────────────────────────────

    private JsonNode fetchTodayHoliday(String isoCode, String country) {
        LocalDate today = LocalDate.now();
        try {
            String url = CALENDARIFIC_URL
                    + "?api_key=" + calendarificKey
                    + "&country=" + isoCode
                    + "&year=" + today.getYear()
                    + "&month=" + today.getMonthValue()
                    + "&day=" + today.getDayOfMonth();

            String response = restClient.get().uri(url).retrieve().body(String.class);
            if (response == null) return null;

            JsonNode root = objectMapper.readTree(response);
            JsonNode holidays = root.path("response").path("holidays");
            if (!holidays.isArray() || holidays.isEmpty()) return null;

            return holidays.get(0);

        } catch (Exception e) {
            log.error("[FestivalMeal] Calendarific request failed for country='{}': {}", country, e.getMessage());
            return null;
        }
    }

    private String extractType(JsonNode holiday) {
        JsonNode typeNode = holiday.path("primary_type");
        if (!typeNode.isMissingNode() && !typeNode.asText().isBlank()) return typeNode.asText();
        JsonNode typesArray = holiday.path("type");
        if (typesArray.isArray() && !typesArray.isEmpty()) return typesArray.get(0).asText();
        return "Holiday";
    }

    // ── Gemini generation + cache ─────────────────────────────────────────────

    private Optional<NotificationVariant> generateAndCache(String country, String language,
                                                            String festivalName, String festivalDesc,
                                                            String festivalType, String festivalDate,
                                                            List<String> recentDishes) {
        String prompt = buildPrompt(country, language, festivalName, festivalDesc, festivalType, festivalDate, recentDishes);
        if (prompt == null) return Optional.empty();

        String response = chatBotApiService.request(prompt);
        if (response == null) {
            log.error("[FestivalMeal] Gemini returned null for festival='{}' country='{}'", festivalName, country);
            return Optional.empty();
        }

        return parseAndCache(response, country, language, festivalName, festivalDate);
    }

    private String buildPrompt(String country, String language,
                                String festivalName, String festivalDesc, String festivalType,
                                String festivalDate, List<String> recentDishes) {
        try {
            String schema = "{"
                    + "\"mood\":\"<joy|sorrow|solemn|neutral>\","
                    + "\"name\":\"<dish name in " + country + "'s official script>\","
                    + "\"nameEnglish\":\"<the ENGLISH TRANSLATION of the dish — what an English speaker would call it (e.g. 'Chicken Pilaf', 'Lamb Curry', 'Rice Pudding'). NOT a transliteration of the local name. Must be plain ASCII, no diacritics>\","
                    + "\"title\":\"<push notification title in " + language + ", max 50 chars, festival-themed>\","
                    + "\"body\":\"<push notification body in " + language + ", max 100 chars, mention the festival>\","
                    + "\"ingredients\":\"<comma-separated ingredients list in " + language + ">\","
                    + "\"cookTime\":\"<ISO 8601 e.g. PT30M>\","
                    + "\"prepTime\":\"<ISO 8601 e.g. PT15M>\","
                    + "\"recipeYield\":\"<e.g. 4 servings>\","
                    + "\"description\":\"<short description in " + language + ">\","
                    + "\"cookingGuide\":\"<clear numbered step-by-step guide in " + language + ">\""
                    + "}";

            ObjectNode root = objectMapper.createObjectNode();
            ArrayNode contents = root.putArray("contents");
            ObjectNode content = contents.addObject();
            ArrayNode parts = content.putArray("parts");

            String avoidClause = recentDishes.isEmpty() ? "" :
                    "IMPORTANT: You have already suggested these dishes recently for " + country + ": ["
                    + String.join(", ", recentDishes)
                    + "]. Do NOT suggest any of these again. Choose a completely different dish. ";

            parts.addObject().put("text",
                    avoidClause
                    + "You are a Michelin-star-level cultural food expert and recipe curator writing for food enthusiasts in " + country + ". "
                    + "Today's date is " + festivalDate + ". "
                    + "Festival: '" + festivalName + "' | Type: " + festivalType + " | Date: " + festivalDate + ". "
                    + (festivalDesc.isBlank() ? "" : "Festival description: " + festivalDesc + " ")
                    + "TASK: "
                    + "1. Determine the mood of this festival (joy, sorrow, solemn, or neutral). "
                    + "   - Joyful festivals (e.g. Eid, Diwali, Christmas, New Year): suggest a showstopper celebratory dish — rich, festive, and indulgent. "
                    + "   - Somber/solemn festivals (e.g. Good Friday, Ashura, mourning days): suggest a refined, respectful traditional dish appropriate for the occasion. "
                    + "   - Neutral/observance: suggest a culturally significant, elevated dish. "
                    + "2. Choose a PREMIUM, restaurant-worthy dish that is iconic for this festival in " + country + ". "
                    + "   Think: the dish families prepare only on special occasions — rich ingredients, elaborate technique, impressive presentation. "
                    + "   Not everyday food. This should feel special, luxurious, and deeply tied to the festival's cultural significance. "
                    + "3. Write a push notification title and body in " + language + " using the following rules: "
                    + "   IDENTIFY the nature of the event from its name and description: "
                    + "   A) FIXED-DATE NATIONAL EVENT (Independence Day, Republic Day, National Day, Revolution Day, etc.): "
                    + "      The title should directly celebrate it — bold, proud, festive. "
                    + "      Example style: 'Happy Independence Day! Cook this today 🍽' or 'It's National Day — celebrate with [dish]!'. "
                    + "   B) RELIGIOUS / LUNAR / VARIABLE EVENT (Eid, Diwali, Christmas, Hanukkah, Ramadan, Vesak, etc.): "
                    + "      The title should be warm and inviting without directly stating the holiday as a greeting (since the date may shift or be a government extension day). "
                    + "      Example style: 'Celebrating Eid? This dish is made for today' or 'A festive recipe for this special day'. "
                    + "   C) GOVERNMENT HOLIDAY / DAY OFF EXTENSION (name contains Holiday, Day Off, etc.): "
                    + "      Do NOT use the word Holiday in the title. Refer to the underlying event if identifiable, otherwise be generic and warm. "
                    + "      Example style: 'Make the most of today with this festive dish'. "
                    + "   The title must be attractive, punchy, and under 50 characters. The body should complement it and mention the dish. "
                    + "RULES: "
                    + "- 'name' must be in the official script of " + country + ". "
                    + "- 'nameEnglish' MUST be the English translation/equivalent of the dish — plain ASCII only, no local script, no diacritics. "
                    + "  The rule is: translate the meaning, do not romanize the local name. "
                    + "  For example (this is just illustrative, not a dish restriction): a chicken rice dish from Bangladesh would be 'Chicken Pilaf', not its local transliteration. "
                    + "  If no standard English name exists, describe it simply in English (e.g. 'Spiced Lamb Rice', 'Sweet Vermicelli Pudding'). "
                    + "- All text fields must be in " + language + ". "
                    + "- 'cookTime' and 'prepTime' in ISO 8601. "
                    + "- CRITICAL JSON RULES: Every string value must stay on a single line. "
                    + "  Use the two-character sequence \\n (backslash + n) for line breaks inside cookingGuide — never a real newline character. "
                    + "  Never use unescaped double-quote characters inside string values. "
                    + "Respond with ONLY valid JSON — no markdown, no extra text. Schema: " + schema);

            ObjectNode genConfig = root.putObject("generationConfig");
            genConfig.put("responseMimeType", "application/json");
            genConfig.put("temperature", 0.7);

            return objectMapper.writeValueAsString(root);
        } catch (Exception e) {
            log.error("[FestivalMeal] Failed to build prompt: {}", e.getMessage());
            return null;
        }
    }

    private Optional<NotificationVariant> parseAndCache(String rawResponse, String country, String language,
                                                         String festivalName, String festivalDate) {
        try {
            JsonNode root = objectMapper.readTree(rawResponse);
            String jsonText = extractGeminiText(root);
            if (jsonText == null) return Optional.empty();

            JsonNode parsed = objectMapper.readTree(sanitizeJson(jsonText));
            String description = parsed.path("description").asText("").trim();
            if (description.isBlank()) {
                log.warn("[FestivalMeal] Gemini returned blank description for festival='{}'", festivalName);
                return Optional.empty();
            }

            String title       = parsed.path("title").asText("").trim();
            String body        = parsed.path("body").asText("").trim();
            String nameEnglish = toAsciiEnglish(parsed.path("nameEnglish").asText("").trim());
            String name        = parsed.path("name").asText(nameEnglish).trim();
            String nameNormalizeEnglish = nameEnglish.toLowerCase().replaceAll("\\s+", " ");
            RecipeCacheEntity entity = RecipeCacheEntity.builder()
                    .recipeId(UUID.randomUUID().toString())
                    .mongoId(null)
                    .name(name)
                    .nameNormalized(nameNormalizeEnglish)
                    .mongoName(nameEnglish)
                    .ingredients(parsed.path("ingredients").asText(null))
                    .cookTime(parsed.path("cookTime").asText(null))
                    .prepTime(parsed.path("prepTime").asText(null))
                    .recipeYield(parsed.path("recipeYield").asText(null))
                    .description(description)
                    .cookingGuide(parsed.path("cookingGuide").asText(null))
                    .country(country)
                    .datePublished(festivalDate)
                    .source("festival-gemini")
                    .recipeReason("festival")
                    .excluded(false)
                    .build();

            entity = saveToCache(entity);

            String extra = "{\"recipeId\":\"" + entity.getRecipeId() + "\"}";
            return Optional.of(NotificationVariant.builder()
                    .audience("general")
                    .title(title.isBlank() ? fallbackTitle(festivalName, name) : title)
                    .body(body.isBlank() ? fallbackBody(description) : body)
                    .extra(extra)
                    .imageUrl(entity.getImage())
                    .build());

        } catch (Exception e) {
            log.error("[FestivalMeal] Failed to parse Gemini response for festival='{}': {}", festivalName, e.getMessage());
            return Optional.empty();
        }
    }

    private RecipeCacheEntity saveToCache(RecipeCacheEntity entity) {
        RecipeCacheEntity saved;
        try {
            saved = recipeCacheRepository.save(entity);
        } catch (Exception e) {
            log.warn("[FestivalMeal] Save failed for '{}' (likely duplicate name) — looking up existing entry", entity.getName());
            // Duplicate name_normalized + country: reuse the existing cached recipe
            return recipeCacheRepository
                    .findByNameNormalizedAndCountryIgnoreCase(entity.getNameNormalized(), entity.getCountry())
                    .orElse(entity);
        }

        String imageUrl = recipeImageService.resolveImage(saved);
        if (imageUrl != null) {
            saved.setImage(imageUrl);
            try {
                saved = recipeCacheRepository.save(saved);
            } catch (Exception e) {
                log.error("[FestivalMeal] Failed to persist image URL for '{}': {}", saved.getName(), e.getMessage());
            }
        }

        return saved;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * If Gemini still returns non-ASCII characters in nameEnglish (e.g. Bengali script
     * or transliteration with diacritics), strip them down to plain ASCII. If nothing
     * remains after stripping, return "Festival Dish" as a safe Pixabay search fallback.
     */
    private String toAsciiEnglish(String value) {
        if (value == null || value.isBlank()) return "Festival Dish";
        // Check if it's already plain ASCII
        if (value.chars().allMatch(c -> c < 128)) return value;
        // Strip non-ASCII characters
        String ascii = value.replaceAll("[^\\x00-\\x7F]", "").trim();
        return ascii.isBlank() ? "Festival Dish" : ascii;
    }

    /**
     * Replaces literal control characters (newlines, tabs, carriage returns) inside
     * JSON string values with their escaped equivalents, so Jackson can parse responses
     * where Gemini emitted real line breaks instead of \n sequences.
     */
    private String sanitizeJson(String json) {
        if (json == null) return null;
        StringBuilder out = new StringBuilder(json.length());
        boolean inString = false;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inString = !inString;
                out.append(c);
            } else if (inString) {
                switch (c) {
                    case '\n' -> out.append("\\n");
                    case '\r' -> out.append("\\r");
                    case '\t' -> out.append("\\t");
                    default   -> out.append(c);
                }
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }

    private String extractGeminiText(JsonNode root) {
        try {
            return root.get("candidates").get(0).get("content").get("parts").get(0).get("text").asText();
        } catch (Exception e) {
            log.error("[FestivalMeal] Unexpected Gemini response structure: {}", e.getMessage());
            return null;
        }
    }

    private String fallbackTitle(String festivalName, String mealName) {
        String t = festivalName + ": Try " + mealName;
        return t.length() > 50 ? t.substring(0, 47) + "..." : t;
    }

    private String fallbackBody(String description) {
        if (description == null) return "A special festive meal for you today!";
        return description.length() > 100 ? description.substring(0, 97) + "..." : description;
    }
}
