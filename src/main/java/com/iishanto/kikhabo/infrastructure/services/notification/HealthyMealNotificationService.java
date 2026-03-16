package com.iishanto.kikhabo.infrastructure.services.notification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.entities.notification.NotificationVariant;
import com.iishanto.kikhabo.infrastructure.model.RecipeCacheEntity;
import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import com.iishanto.kikhabo.infrastructure.repositories.database.RecipeCacheRepository;
import com.iishanto.kikhabo.infrastructure.services.chatbot.ChatBotApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    public HealthyMealNotificationService(ChatBotApiService chatBotApiService,
                                          RecipeCacheRepository recipeCacheRepository,
                                          MongoTemplate mongoTemplate,
                                          ObjectMapper objectMapper) {
        this.chatBotApiService = chatBotApiService;
        this.recipeCacheRepository = recipeCacheRepository;
        this.mongoTemplate = mongoTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Generates up to {@code count} healthy meal notification variants for a country.*
     * Flow per variant:
     *   1. Ask Gemini for well-known healthy recipe names + notification text in user's language
     *   2. For each recipe name: search MySQL → MongoDB → Gemini full generation
     *   3. Return NotificationVariant with extra carrying the recipe_id for deep link
     */
    public List<NotificationVariant> generateVariants(String country, String language, String timeSlot, int count) {
        List<SuggestedMeal> suggestions = suggestMealNames(country, language, timeSlot, count);
        if (suggestions.isEmpty()) {
            log.warn("[HealthyMeal] No meal suggestions from Gemini for country={}", country);
            return List.of();
        }

        List<NotificationVariant> variants = new ArrayList<>();
        for (SuggestedMeal suggestion : suggestions) {
            RecipeCacheEntity recipe = resolveRecipe(suggestion, country, language);
            if (recipe == null) {
                log.warn("[HealthyMeal] Could not resolve recipe '{}' for country={}, skipping", suggestion.name(), country);
                continue;
            }
            String extra = "{\"recipeId\":\"" + recipe.getRecipeId() + "\"}";
            variants.add(NotificationVariant.builder()
                    .audience("general")
                    .title(suggestion.title())
                    .body(suggestion.body())
                    .extra(extra)
                    .build());
            log.info("[HealthyMeal] Resolved variant: recipe='{}' id='{}' country={}", recipe.getName(), recipe.getRecipeId(), country);
        }
        return variants;
    }

    // ── Step 1: Ask Gemini for well-known recipe names + notification text ────

    private List<SuggestedMeal> suggestMealNames(String country, String language, String timeSlot, int count) {
        String prompt = buildSuggestionPrompt(country, language, timeSlot, count);
        if (prompt == null) return List.of();

        String response = chatBotApiService.request(prompt);
        if (response == null) {
            log.error("[HealthyMeal] Gemini returned null for meal suggestion, country={}", country);
            return List.of();
        }
        return parseSuggestions(response);
    }

    private String buildSuggestionPrompt(String country, String language, String timeSlot, int count) {
        try {
            StringBuilder schemaItems = new StringBuilder();
            for (int i = 0; i < count; i++) {
                if (i > 0) schemaItems.append(",");
                schemaItems.append("{\"name\":\"<dish name in official local script>\",\"nameEnglish\":\"<dish name in English Roman alphabet>\",\"title\":\"<notification title max 50 chars>\",\"body\":\"<notification body max 100 chars>\"}");
            }
            String schema = "{\"meals\":[" + schemaItems + "]}";

            com.fasterxml.jackson.databind.node.ObjectNode root = objectMapper.createObjectNode();
            com.fasterxml.jackson.databind.node.ArrayNode contents = root.putArray("contents");
            com.fasterxml.jackson.databind.node.ObjectNode content = contents.addObject();
            com.fasterxml.jackson.databind.node.ArrayNode parts = content.putArray("parts");

            parts.addObject().put("text",
                    "You are a nutrition expert and food cultural advisor. " +
                    "Your task is to suggest well-known, genuinely popular healthy " + timeSlot + " recipes from " + country + ". " +
                    "IMPORTANT RULES: " +
                    "1. Only suggest recipes that are truly well-known and traditional in " + country + ". Do NOT invent recipes. " +
                    "2. 'name' must be the dish name written in the official language/script of " + country + " (e.g. Bengali script for Bangladesh). " +
                    "3. 'nameEnglish' must be the same dish name in English Roman alphabet only (e.g. 'Macher Jhol'). Used for search — must be ASCII-safe.");

            parts.addObject().put("text",
                    "Generate exactly " + count + " healthy meal suggestion(s) for the '" + timeSlot + "' time slot in " + country + ". " +
                    "For each suggestion: " +
                    "1) 'name': dish name in the official script/language of " + country + " — for display to users. " +
                    "2) 'nameEnglish': the same dish in English Roman alphabet ONLY — no local script whatsoever. " +
                    "3) 'title': push notification title (max 50 chars) in " + language + ". " +
                    "4) 'body': push notification body (max 100 chars) in " + language + " highlighting health benefits. " +
                    "Ensure all suggestions are genuinely healthy and appropriate for " + timeSlot + ".");

            parts.addObject().put("text",
                    "Respond with ONLY a valid JSON object — no markdown, no extra text. Schema: " + schema);

            com.fasterxml.jackson.databind.node.ObjectNode genConfig = root.putObject("generationConfig");
            genConfig.put("responseMimeType", "application/json");
            genConfig.put("temperature", 0.7);

            return objectMapper.writeValueAsString(root);
        } catch (Exception e) {
            log.error("[HealthyMeal] Failed to build suggestion prompt: {}", e.getMessage());
            return null;
        }
    }

    private List<SuggestedMeal> parseSuggestions(String rawResponse) {
        List<SuggestedMeal> result = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(rawResponse);
            String jsonText = extractGeminiText(root);
            if (jsonText == null) return List.of();

            JsonNode parsed = objectMapper.readTree(jsonText);
            JsonNode meals = parsed.get("meals");
            if (meals == null || !meals.isArray()) return List.of();

            for (JsonNode meal : meals) {
                String name        = meal.path("name").asText("").trim();
                String nameEnglish = meal.path("nameEnglish").asText("").trim();
                String title       = meal.path("title").asText("").trim();
                String body        = meal.path("body").asText("").trim();
                if (!nameEnglish.isBlank() && !title.isBlank()) {
                    result.add(new SuggestedMeal(name.isBlank() ? nameEnglish : name, nameEnglish, title, body));
                }
            }
        } catch (Exception e) {
            log.error("[HealthyMeal] Failed to parse suggestion response: {}", e.getMessage());
        }
        return result;
    }

    // ── Step 2: Resolve recipe — MySQL → MongoDB → Gemini full generation ─────

    private RecipeCacheEntity resolveRecipe(SuggestedMeal suggestion, String country, String language) {
        // nameEnglish is used for all lookups — ASCII-safe, no encoding issues
        String normalized = suggestion.nameEnglish().toLowerCase().trim();

        // MySQL lookup by English normalized name
        RecipeCacheEntity cached = recipeCacheRepository.findByNameNormalized(normalized).orElse(null);
        if (cached != null) {
            log.info("[HealthyMeal] Cache hit in MySQL for '{}'", suggestion.nameEnglish());
            return cached;
        }

        // MongoDB lookup by English name (case-insensitive regex on ASCII name)
        RecipeDocument mongoDoc = mongoTemplate.findOne(
                Query.query(Criteria.where("name").regex("^" + escapeRegex(suggestion.nameEnglish()) + "$", "i")),
                RecipeDocument.class
        );
        if (mongoDoc != null) {
            log.info("[HealthyMeal] Found '{}' in MongoDB, caching to MySQL", suggestion.nameEnglish());
            return saveToCache(RecipeCacheEntity.builder()
                    .recipeId(mongoDoc.getId())
                    .mongoId(mongoDoc.getId())
                    .name(suggestion.name())           // local-script display name
                    .nameNormalized(normalized)         // English romanized for dedup
                    .ingredients(mongoDoc.getIngredients())
                    .url(mongoDoc.getUrl())
                    .image(mongoDoc.getImage())
                    .source(mongoDoc.getSource())
                    .recipeYield(mongoDoc.getRecipeYield())
                    .datePublished(mongoDoc.getDatePublished())
                    .cookTime(mongoDoc.getCookTime())
                    .prepTime(mongoDoc.getPrepTime())
                    .description(mongoDoc.getDescription())
                    .country(country)
                    .cookingGuide(mongoDoc.getCookingGuide())
                    .excluded(mongoDoc.isExcluded())
                    .build());
        }

        // Gemini full recipe generation (last resort) — uses English name to avoid encoding issues
        log.info("[HealthyMeal] '{}' not found anywhere, generating full recipe via Gemini", suggestion.nameEnglish());
        return generateAndCache(suggestion, country, language);
    }

    private RecipeCacheEntity generateAndCache(SuggestedMeal suggestion, String country, String language) {
        String prompt = buildFullRecipePrompt(suggestion.nameEnglish(), country, language);
        if (prompt == null) return null;

        String response = chatBotApiService.request(prompt);
        if (response == null) return null;

        RecipeCacheEntity entity = parseFullRecipe(response, suggestion, country);
        if (entity == null) return null;

        return saveToCache(entity);
    }

    private String buildFullRecipePrompt(String name, String country, String language) {
        try {
            String schema = "{\"name\":\"string\",\"ingredients\":\"comma-separated list\",\"cookTime\":\"e.g. PT30M\",\"prepTime\":\"e.g. PT15M\",\"recipeYield\":\"e.g. 4 servings\",\"description\":\"short description\",\"cookingGuide\":\"step-by-step guide\"}";

            com.fasterxml.jackson.databind.node.ObjectNode root = objectMapper.createObjectNode();
            com.fasterxml.jackson.databind.node.ArrayNode contents = root.putArray("contents");
            com.fasterxml.jackson.databind.node.ObjectNode content = contents.addObject();
            com.fasterxml.jackson.databind.node.ArrayNode parts = content.putArray("parts");

            parts.addObject().put("text",
                    "You are a professional chef and food expert writing for home cooks in " + country + ". " +
                    "Generate accurate recipe details for '" + name + "', a traditional dish from " + country + ". " +
                    "IMPORTANT RULES: " +
                    "1. This must be the authentic, well-known recipe — do not invent or alter it. " +
                    "2. If you do not recognize '" + name + "' as a real dish, set 'description' to 'UNRECOGNIZED' and leave other fields empty. " +
                    "3. Write ALL text fields (description, ingredients, cookingGuide, recipeYield) in " + language + " — the official language of " + country + ". " +
                    "4. Only cookTime and prepTime should remain in ISO 8601 format (e.g. PT30M). " +
                    "5. The cookingGuide must be clear numbered steps a home cook can follow.");

            parts.addObject().put("text",
                    "Respond with ONLY a valid JSON object — no markdown, no extra text. Schema: " + schema);

            com.fasterxml.jackson.databind.node.ObjectNode genConfig = root.putObject("generationConfig");
            genConfig.put("responseMimeType", "application/json");
            genConfig.put("temperature", 0.4);

            return objectMapper.writeValueAsString(root);
        } catch (Exception e) {
            log.error("[HealthyMeal] Failed to build full recipe prompt for '{}': {}", name, e.getMessage());
            return null;
        }
    }

    private RecipeCacheEntity parseFullRecipe(String rawResponse, SuggestedMeal suggestion, String country) {
        try {
            JsonNode root = objectMapper.readTree(rawResponse);
            String jsonText = extractGeminiText(root);
            if (jsonText == null) return null;

            JsonNode parsed = objectMapper.readTree(jsonText);
            String description = parsed.path("description").asText("").trim();
            if ("UNRECOGNIZED".equalsIgnoreCase(description)) {
                log.warn("[HealthyMeal] Gemini does not recognize '{}' as a real dish — discarding", suggestion.nameEnglish());
                return null;
            }

            return RecipeCacheEntity.builder()
                    .recipeId(UUID.randomUUID().toString())
                    .mongoId(null)
                    .name(suggestion.name())                              // local-script display name
                    .nameNormalized(suggestion.nameEnglish().toLowerCase().trim()) // English dedup key
                    .ingredients(parsed.path("ingredients").asText(null))
                    .cookTime(parsed.path("cookTime").asText(null))
                    .prepTime(parsed.path("prepTime").asText(null))
                    .recipeYield(parsed.path("recipeYield").asText(null))
                    .description(parsed.path("description").asText(null))
                    .cookingGuide(parsed.path("cookingGuide").asText(null))
                    .country(country)
                    .source("gemini")
                    .excluded(false)
                    .build();
        } catch (Exception e) {
            log.error("[HealthyMeal] Failed to parse full recipe response for '{}': {}", suggestion.nameEnglish(), e.getMessage());
            return null;
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private RecipeCacheEntity saveToCache(RecipeCacheEntity entity) {
        try {
            return recipeCacheRepository.save(entity);
        } catch (Exception e) {
            log.error("[HealthyMeal] Failed to save recipe '{}' to MySQL cache: {}", entity.getName(), e.getMessage());
            return entity; // still usable even if save failed
        }
    }

    private String extractGeminiText(JsonNode root) {
        try {
            return root.get("candidates").get(0).get("content").get("parts").get(0).get("text").asText();
        } catch (Exception e) {
            log.error("[HealthyMeal] Unexpected Gemini response structure: {}", e.getMessage());
            return null;
        }
    }

    private String escapeRegex(String input) {
        return input.replaceAll("([\\\\+*?\\[^\\]$(){}=!<>|:\\-#])", "\\\\$1");
    }

    /**
     * name        — display name in the country's official language (e.g. "পাঙাশ ভুনা")
     * nameEnglish — romanized English name used for DB lookup and Gemini prompts (e.g. "Pangash Bhuna")
     */
    private record SuggestedMeal(String name, String nameEnglish, String title, String body) {}
}
