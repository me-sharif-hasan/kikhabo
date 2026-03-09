package com.iishanto.kikhabo.infrastructure.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.datasource.NotificationContentDataSource;
import com.iishanto.kikhabo.domain.entities.notification.NotificationVariant;
import com.iishanto.kikhabo.infrastructure.services.chatbot.ChatBotApiService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class NotificationContentDataSourceImpl implements NotificationContentDataSource {

    private final ChatBotApiService chatBotApiService;
    private final ObjectMapper objectMapper;
    private final Logger logger;

    private String buildEngagementPrompt() {
        try {
            String schema = "{\"variants\":[{\"audience\":\"female\",\"title\":\"<title max 50 chars>\",\"body\":\"<body max 100 chars>\",\"extra\":\"\"},{\"audience\":\"male\",\"title\":\"...\",\"body\":\"...\",\"extra\":\"\"},{\"audience\":\"general\",\"title\":\"...\",\"body\":\"...\",\"extra\":\"\"}]}";
            com.fasterxml.jackson.databind.node.ObjectNode root = objectMapper.createObjectNode();
            com.fasterxml.jackson.databind.node.ArrayNode contents = root.putArray("contents");
            com.fasterxml.jackson.databind.node.ObjectNode content = contents.addObject();
            com.fasterxml.jackson.databind.node.ArrayNode parts = content.putArray("parts");

            parts.addObject().put("text",
                    "You are a creative mobile app copywriter for Kikhabo, a smart meal planning app that helps " +
                    "families plan meals, track nutrition, manage grocery budgets, and discover new recipes. " +
                    "Write engaging push notification content that motivates users to open the app.");

            parts.addObject().put("text",
                    "Generate exactly 3 push notification variants for today's daily engagement push. " +
                    "Variant 1 — female audience: nurturing, caring, family-focused tone. " +
                    "Variant 2 — male audience: practical, goal-oriented, efficiency-focused tone. " +
                    "Variant 3 — general audience: neutral and inclusive. " +
                    "Reference things like saving money on groceries, eating healthy, trying new recipes, or helping the family. " +
                    "Vary the angle for each variant so they feel distinct and fresh.");

            parts.addObject().put("text",
                    "Generator seed: " + System.currentTimeMillis() + ". Use this seed to randomize so each day feels different.");

            parts.addObject().put("text",
                    "Respond with ONLY a valid JSON object — no markdown, no extra text. Schema: " + schema);

            com.fasterxml.jackson.databind.node.ObjectNode genConfig = root.putObject("generationConfig");
            genConfig.put("responseMimeType", "application/json");
            genConfig.put("temperature", 1.8);

            return objectMapper.writeValueAsString(root);
        } catch (Exception e) {
            logger.error("[DailyNotification] Failed to build prompt JSON: {}", e.getMessage());
            return null;
        }
    }

    private List<NotificationVariant> parseVariants(String rawResponse) {
        List<NotificationVariant> variants = new ArrayList<>();
        try {
            // Gemini wraps the response — extract the text content
            JsonNode root = objectMapper.readTree(rawResponse);
            String jsonText = extractTextFromGeminiResponse(root);
            if (jsonText == null) {
                logger.error("[DailyNotification] Could not extract text from Gemini response");
                return List.of();
            }

            JsonNode parsed = objectMapper.readTree(jsonText);
            JsonNode variantsNode = parsed.get("variants");
            if (variantsNode == null || !variantsNode.isArray()) {
                logger.error("[DailyNotification] 'variants' array missing in Gemini response");
                return List.of();
            }

            for (JsonNode v : variantsNode) {
                String audience = v.path("audience").asText("");
                String title = v.path("title").asText("");
                String body = v.path("body").asText("");
                String extra = v.path("extra").asText("");
                if (!title.isBlank() && !body.isBlank()) {
                    variants.add(NotificationVariant.builder()
                            .audience(audience)
                            .title(title)
                            .body(body)
                            .extra(extra)
                            .build());
                    logger.info("[DailyNotification] Parsed variant — audience: {}, title: {}", audience, title);
                } else {
                    logger.warn("[DailyNotification] Skipping variant with blank title/body for audience: {}", audience);
                }
            }
        } catch (Exception e) {
            logger.error("[DailyNotification] Failed to parse Gemini response: {}", e.getMessage());
        }
        return variants;
    }

    // ── Country-specific variant generation ─────────────────────────────────────

    @Override
    public List<NotificationVariant> generateCountryVariants(String country, String language, String timeSlot, int count) {
        String prompt = buildCountryPrompt(country, language, timeSlot, count);
        if (prompt == null) {
            logger.error("[CountryNotification] Failed to build prompt for country={}", country);
            return List.of();
        }
        logger.info("[CountryNotification] Requesting {} variant(s) for country={} lang={} slot={}", count, country, language, timeSlot);
        String response = chatBotApiService.request(prompt);
        if (response == null) {
            logger.error("[CountryNotification] Gemini returned null response for country={}", country);
            return List.of();
        }
        return parseVariants(response);
    }

    private String buildCountryPrompt(String country, String language, String timeSlot, int count) {
        try {
            // Build schema example with `count` variant slots
            StringBuilder schemaVariants = new StringBuilder();
            for (int i = 0; i < count; i++) {
                if (i > 0) schemaVariants.append(",");
                schemaVariants.append("{\"audience\":\"general\",\"title\":\"<title max 50 chars>\",\"body\":\"<body max 100 chars>\",\"extra\":\"\"}");
            }
            String schema = "{\"variants\":[" + schemaVariants + "]}";

            String safeSlot = (timeSlot != null) ? timeSlot : "meal";
            String nutritionHint = switch (safeSlot) {
                case "breakfast" -> "morning breakfast nutrition — e.g. protein, fibre, energy to start the day";
                case "lunch"     -> "midday lunch nutrition — e.g. balanced macros, hydration, brain fuel";
                default          -> "healthy eating — e.g. balanced macros, vitamins, staying energised through the day";
            };

            com.fasterxml.jackson.databind.node.ObjectNode root = objectMapper.createObjectNode();
            com.fasterxml.jackson.databind.node.ArrayNode contents = root.putArray("contents");
            com.fasterxml.jackson.databind.node.ObjectNode content = contents.addObject();
            com.fasterxml.jackson.databind.node.ArrayNode parts = content.putArray("parts");

            parts.addObject().put("text",
                    "You are a witty, culturally-aware mobile app copywriter for Kikhabo, a smart meal planning app. " +
                    "Your job is to craft funny, friendly push notifications that also sneak in useful nutrition tips. " +
                    "Write in " + language + " (the primary language of " + country + "). " +
                    "The tone must be playful and humorous — think of a funny friend who also knows a lot about food. " +
                    "Culturally relevant jokes or food references from " + country + " are very welcome.");

            parts.addObject().put("text",
                    "Generate exactly " + count + " push notification variant(s) for the '" + safeSlot + "' time slot. " +
                    "Each notification MUST: " +
                    "1) Be written entirely in " + language + ". " +
                    "2) Include a funny, light-hearted joke or witty remark relevant to " + country + " food culture. " +
                    "3) Weave in a genuine nutrition fact about " + nutritionHint + ". " +
                    "4) Encourage the user to open Kikhabo to plan their " + safeSlot + ". " +
                    "Keep the title under 50 characters and the body under 100 characters.");

            parts.addObject().put("text",
                    "Generator seed: " + System.currentTimeMillis() + ". Vary the jokes so variants feel distinct.");

            parts.addObject().put("text",
                    "Respond with ONLY a valid JSON object — no markdown, no extra text. Schema: " + schema);

            com.fasterxml.jackson.databind.node.ObjectNode genConfig = root.putObject("generationConfig");
            genConfig.put("responseMimeType", "application/json");
            genConfig.put("temperature", 1.8);

            return objectMapper.writeValueAsString(root);
        } catch (Exception e) {
            logger.error("[CountryNotification] Failed to build country prompt: {}", e.getMessage());
            return null;
        }
    }

    // ── Shared helpers ───────────────────────────────────────────────────────────

    private String extractTextFromGeminiResponse(JsonNode root) {
        try {
            return root.get("candidates")
                    .get(0)
                    .get("content")
                    .get("parts")
                    .get(0)
                    .get("text")
                    .asText();
        } catch (Exception e) {
            logger.error("[DailyNotification] Unexpected Gemini response structure: {}", e.getMessage());
            return null;
        }
    }
}
