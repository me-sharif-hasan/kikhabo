package com.iishanto.kikhabo.infrastructure.services.notification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iishanto.kikhabo.domain.entities.notification.NotificationVariant;
import com.iishanto.kikhabo.infrastructure.model.RecipeCacheEntity;
import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import com.iishanto.kikhabo.infrastructure.repositories.database.RecipeCacheRepository;
import com.iishanto.kikhabo.infrastructure.services.chatbot.ChatBotApiService;
import com.iishanto.kikhabo.infrastructure.services.recipe.CountryService;
import com.iishanto.kikhabo.infrastructure.services.recipe.RecommendationService;
import com.iishanto.kikhabo.infrastructure.services.recipe.RecipeImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HealthyMealNotificationService {

    private static final Logger log = LoggerFactory.getLogger(HealthyMealNotificationService.class);

    private final ChatBotApiService chatBotApiService;
    private final RecipeCacheRepository recipeCacheRepository;
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper;
    private final CountryService countryService;
    private final RecommendationService recommendationService;
    private final RecipeImageService recipeImageService;

    public HealthyMealNotificationService(ChatBotApiService chatBotApiService,
                                          RecipeCacheRepository recipeCacheRepository,
                                          MongoTemplate mongoTemplate,
                                          ObjectMapper objectMapper,
                                          CountryService countryService,
                                          RecommendationService recommendationService,
                                          RecipeImageService recipeImageService) {
        this.chatBotApiService = chatBotApiService;
        this.recipeCacheRepository = recipeCacheRepository;
        this.mongoTemplate = mongoTemplate;
        this.objectMapper = objectMapper;
        this.countryService = countryService;
        this.recommendationService = recommendationService;
        this.recipeImageService = recipeImageService;
    }

    /**
     * Generates up to {@code count} healthy meal notification variants for a country.
     * New flow:
     *   1. Resolve country → continent/subcontinent via CountryService (countries.json)
     *   2. Get regional cuisine tags from RecommendationService (recomendation.json)
     *   3. Search MongoDB for non-excluded seed recipes matching those tags
     *   4. Shuffle seeds; for each seed check composite-key cache (mongoId + country)
     *   5. On cache miss: Gemini fine-tunes the seed into a country-specific recipe
     *      and returns notification title/body in the user's language in one call
     */
    public List<NotificationVariant> generateVariants(String country, String language, String timeSlot, int count) {
        // Step 1: Resolve region
        CountryService.Region region = countryService.resolve(country);
        if (region == null) {
            log.warn("[HealthyMeal] Unrecognized country='{}', cannot resolve region", country);
            return List.of();
        }

        // Step 2: Get cuisine tags for this region
        List<String> tags = recommendationService.getTerms(region.getContinent(), region.getSubcontinent());
        if (tags.isEmpty()) {
            log.warn("[HealthyMeal] No cuisine tags for country={} ({}/{})",
                    country, region.getContinent(), region.getSubcontinent());
            return List.of();
        }

        // Step 3: Find seed recipes in MongoDB matching regional tags
        List<RecipeDocument> seeds = findSeedRecipes(tags, count * 3);
        if (seeds.isEmpty()) {
            log.warn("[HealthyMeal] No seed recipes found in MongoDB for country={}", country);
            return List.of();
        }
        // Step 4: Resolve each seed → NotificationVariant
        List<NotificationVariant> variants = new ArrayList<>();
        for (RecipeDocument seed : seeds) {
            if (variants.size() >= count) break;
            SeedResult result = resolveFromSeed(seed, country, language, timeSlot);
            if (result == null) continue;

            String extra = "{\"recipeId\":\"" + result.entity().getRecipeId() + "\"}";
            variants.add(NotificationVariant.builder()
                    .audience("general")
                    .title(result.title())
                    .body(result.body())
                    .extra(extra)
                    .imageUrl(result.entity().getImage())
                    .build());
            log.info("[HealthyMeal] Variant resolved: recipe='{}' id='{}' country={}",
                    result.entity().getName(), result.entity().getRecipeId(), country);
        }
        return variants;
    }

    // ── MongoDB seed search ───────────────────────────────────────────────────

    /**
     * Mirrors GetRandomRecipesUseCase.buildContinentCriteria:
     * regex OR on name/ingredients across regional tags, then $sample for random results.
     */
    private List<RecipeDocument> findSeedRecipes(List<String> tags, int limit) {
        String pattern = String.join("|", tags);
        Criteria tagMatch = new Criteria().orOperator(
                Criteria.where("name").regex(pattern, "i"),
                Criteria.where("ingredients").regex(pattern, "i")
        );
        Criteria finalCriteria = new Criteria().andOperator(
                Criteria.where("excluded").ne(true),
                tagMatch
        );

        AggregationResults<RecipeDocument> results = mongoTemplate.aggregate(
                Aggregation.newAggregation(
                        Aggregation.match(finalCriteria),
                        Aggregation.sample(limit)
                ),
                RecipeDocument.class,
                RecipeDocument.class
        );
        return results.getMappedResults();
    }

    // ── Cache check + Gemini fine-tune ────────────────────────────────────────

    private SeedResult resolveFromSeed(RecipeDocument seed, String country, String language, String timeSlot) {
        // Composite-key cache check: (mongoId, country) — one entry per recipe per country
        RecipeCacheEntity cached = recipeCacheRepository
                .findByMongoIdAndCountryIgnoreCase(seed.getId(), country)
                .orElse(null);

        if (cached != null) {
            log.info("[HealthyMeal] Composite cache hit: mongoId='{}' country='{}'", seed.getId(), country);
            return new SeedResult(cached,
                    fallbackTitle(cached.getName()),
                    fallbackBody(cached.getDescription()));
        }

        // Cache miss — Gemini fine-tunes the seed for this country/language
        log.info("[HealthyMeal] Cache miss — using MongoDB seed: id='{}' name='{}' → fine-tuning for country={}",
                seed.getId(), seed.getName(), country);
        return generateAndCacheFromSeed(seed, country, language, timeSlot);
    }

    private SeedResult generateAndCacheFromSeed(RecipeDocument seed, String country, String language, String timeSlot) {
        String prompt = buildFineTunePrompt(seed, country, language, timeSlot);
        if (prompt == null) return null;

        String response = chatBotApiService.request(prompt);
        if (response == null) {
            log.error("[HealthyMeal] Gemini returned null for seed='{}' country={}", seed.getName(), country);
            return null;
        }

        return parseAndCache(response, seed, country);
    }

    // ── Gemini prompt: seed → country-specific recipe + notification text ─────

    private String buildFineTunePrompt(RecipeDocument seed, String country, String language, String timeSlot) {
        try {
            String schema = "{"
                    + "\"name\":\"<dish name in " + country + "s official script>\","
                    + "\"nameEnglish\":\"<the ENGLISH TRANSLATION of the dish — what an English speaker would call it (e.g. 'Chicken Pilaf', 'Lamb Curry'). NOT a transliteration. Plain ASCII only, no diacritics>\","
                    + "\"title\":\"<push notification title in " + language + ", max 50 chars>\","
                    + "\"body\":\"<push notification body in " + language + ", max 100 chars, highlight health benefits>\","
                    + "\"ingredients\":\"<adapted comma-separated ingredient list for " + country + ">\","
                    + "\"cookTime\":\"<ISO 8601 e.g. PT30M>\","
                    + "\"prepTime\":\"<ISO 8601 e.g. PT15M>\","
                    + "\"recipeYield\":\"<e.g. 4 servings>\","
                    + "\"description\":\"<short description in " + language + ">\","
                    + "\"cookingGuide\":\"<clear numbered step-by-step guide in " + language + ">\""
                    + "}";

            String seedContext = "Recipe from database:\n"
                    + "Name: " + seed.getName() + "\n"
                    + (seed.getIngredients() != null ? "Ingredients: " + seed.getIngredients() + "\n" : "")
                    + (seed.getDescription() != null ? "Description: " + seed.getDescription() + "\n" : "")
                    + (seed.getCookTime() != null ? "Cook time: " + seed.getCookTime() + "\n" : "")
                    + (seed.getPrepTime() != null ? "Prep time: " + seed.getPrepTime() + "\n" : "");

            ObjectNode root = objectMapper.createObjectNode();
            com.fasterxml.jackson.databind.node.ArrayNode contents = root.putArray("contents");
            com.fasterxml.jackson.databind.node.ObjectNode content = contents.addObject();
            com.fasterxml.jackson.databind.node.ArrayNode parts = content.putArray("parts");

            parts.addObject().put("text",
                    "You are a recipe expert writing a food blog for home cooks in " + country + ". "
                    + "You have been given a recipe and your job is to present it naturally for your audience. "
                    + "APPROACH: "
                    + "- If this recipe has a well-known traditional equivalent or local variant in " + country + ", present that version — use its real local name and authentic preparation. "
                    + "- If no local variant exists, naturally adapt it for " + country + " home cooks: swap hard-to-find ingredients with locally available ones, adjust seasoning to local taste, and write it as a knowledgeable food blogger would — warm, natural, and authentic-feeling. "
                    + "- Either way, the result must feel like a real recipe a home cook in " + country + " would actually make for " + timeSlot + ". "
                    + "RULES: "
                    + "1. 'name' must be in the official script/language of " + country + " (e.g. Bengali script for Bangladesh). "
                    + "2. 'nameEnglish' MUST be the English translation/equivalent of the dish — plain ASCII only, no local script, no diacritics. "
                    + "   The rule is: translate the meaning, do not romanize the local name. "
                    + "   For example (illustrative only, not a dish restriction): a chicken rice dish would be 'Chicken Pilaf', not its local transliteration. "
                    + "   If no standard English name exists, describe it simply in English (e.g. 'Spiced Lamb Rice', 'Sweet Vermicelli Pudding'). "
                    + "3. All text fields (description, ingredients, cookingGuide, recipeYield) must be in " + language + ". "
                    + "4. 'title' and 'body' are push notification strings in " + language + " — concise, enticing, written like a food blogger teaser. "
                    + "5. 'cookTime' and 'prepTime' must remain in ISO 8601 format (e.g. PT30M).");

            parts.addObject().put("text", seedContext);

            parts.addObject().put("text",
                    "Present this recipe naturally for " + country + " home cooks for " + timeSlot
                    + ". Respond with ONLY valid JSON — no markdown, no extra text. Schema: " + schema);

            com.fasterxml.jackson.databind.node.ObjectNode genConfig = root.putObject("generationConfig");
            genConfig.put("responseMimeType", "application/json");
            genConfig.put("temperature", 0.5);

            return objectMapper.writeValueAsString(root);
        } catch (Exception e) {
            log.error("[HealthyMeal] Failed to build fine-tune prompt for seed='{}': {}", seed.getName(), e.getMessage());
            return null;
        }
    }

    private SeedResult parseAndCache(String rawResponse, RecipeDocument seed, String country) {
        try {
            JsonNode root = objectMapper.readTree(rawResponse);
            String jsonText = extractGeminiText(root);
            if (jsonText == null) return null;

            JsonNode parsed = objectMapper.readTree(jsonText);
            String description = parsed.path("description").asText("").trim();
            if (description.isBlank()) {
                log.warn("[HealthyMeal] Gemini returned blank description for seed='{}', skipping", seed.getName());
                return null;
            }

            String title       = parsed.path("title").asText("").trim();
            String body        = parsed.path("body").asText("").trim();
            String nameEnglish = parsed.path("nameEnglish").asText(seed.getName()).trim();
            String name        = parsed.path("name").asText(nameEnglish).trim();

            RecipeCacheEntity entity = RecipeCacheEntity.builder()
                    .recipeId(UUID.randomUUID().toString())
                    .mongoId(seed.getId())                              // composite dedup: mongoId
                    .name(name)
                    .nameNormalized(nameEnglish.toLowerCase().trim())
                    .mongoName(seed.getName())                         // original MongoDB name for Pexels search
                    .ingredients(parsed.path("ingredients").asText(seed.getIngredients()))
                    .url(seed.getUrl())
                    .image(seed.getImage())
                    .cookTime(parsed.path("cookTime").asText(seed.getCookTime()))
                    .prepTime(parsed.path("prepTime").asText(seed.getPrepTime()))
                    .recipeYield(parsed.path("recipeYield").asText(seed.getRecipeYield()))
                    .description(description)
                    .cookingGuide(parsed.path("cookingGuide").asText(null))
                    .country(country)                                  // composite dedup: country
                    .source("gemini-seeded")
                    .excluded(false)
                    .build();

            RecipeCacheEntity saved = saveToCache(entity);
            return new SeedResult(saved,
                    title.isBlank() ? fallbackTitle(name) : title,
                    body.isBlank()  ? fallbackBody(description) : body);
        } catch (Exception e) {
            log.error("[HealthyMeal] Failed to parse fine-tune response for seed='{}': {}", seed.getName(), e.getMessage());
            return null;
        }
    }

    // ── Fallback notification text for cache hits ─────────────────────────────

    private String fallbackTitle(String name) {
        if (name == null) return "Healthy Meal for You";
        String t = "Try: " + name;
        return t.length() > 50 ? t.substring(0, 47) + "..." : t;
    }

    private String fallbackBody(String description) {
        if (description == null) return "A healthy traditional meal for you today!";
        return description.length() > 100 ? description.substring(0, 97) + "..." : description;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private RecipeCacheEntity saveToCache(RecipeCacheEntity entity) {
        RecipeCacheEntity saved;
        try {
            saved = recipeCacheRepository.save(entity);
        } catch (Exception e) {
            log.error("[HealthyMeal] Failed to save recipe '{}' to MySQL cache: {}", entity.getName(), e.getMessage());
            return entity; // still usable even if save fails
        }

        // Resolve image: MongoDB URL → S3 (if valid) or Pexels fallback
        String imageUrl = recipeImageService.resolveImage(saved);
        if (imageUrl != null) {
            saved.setImage(imageUrl);
            try {
                saved = recipeCacheRepository.save(saved);
            } catch (Exception e) {
                log.error("[HealthyMeal] Failed to persist image URL for '{}': {}", saved.getName(), e.getMessage());
            }
        }

        return saved;
    }

    private String extractGeminiText(JsonNode root) {
        try {
            return root.get("candidates").get(0).get("content").get("parts").get(0).get("text").asText();
        } catch (Exception e) {
            log.error("[HealthyMeal] Unexpected Gemini response structure: {}", e.getMessage());
            return null;
        }
    }

    private record SeedResult(RecipeCacheEntity entity, String title, String body) {}
}
