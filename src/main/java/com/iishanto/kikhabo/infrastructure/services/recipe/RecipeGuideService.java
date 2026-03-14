package com.iishanto.kikhabo.infrastructure.services.recipe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import com.iishanto.kikhabo.infrastructure.services.chatbot.ChatBotApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class RecipeGuideService {

    private static final Logger log = LoggerFactory.getLogger(RecipeGuideService.class);

    private final MongoTemplate mongoTemplate;
    private final ChatBotApiService chatBotApiService;
    private final ObjectMapper objectMapper;

    public RecipeGuideService(MongoTemplate mongoTemplate,
                              ChatBotApiService chatBotApiService,
                              ObjectMapper objectMapper) {
        this.mongoTemplate = mongoTemplate;
        this.chatBotApiService = chatBotApiService;
        this.objectMapper = objectMapper;
    }

    /**
     * Returns the cooking guide for a recipe.
     * If already cached in MongoDB → returns immediately.
     * If not → generates via Gemini, stores, then returns.
     */
    public String getOrGenerate(RecipeDocument recipe) {
        if (recipe.getCookingGuide() != null && !recipe.getCookingGuide().isBlank()) {
            return recipe.getCookingGuide();
        }

        log.info("[guide] generating cooking guide for: {}", recipe.getName());
        String guide = generateFromGemini(recipe);

        if (guide != null && !guide.isBlank()) {
            mongoTemplate.updateFirst(
                    Query.query(Criteria.where("_id").is(recipe.getId())),
                    Update.update("cookingGuide", guide),
                    RecipeDocument.class
            );
            log.info("[guide] cached guide for: {}", recipe.getName());
        }

        return guide;
    }

    private String generateFromGemini(RecipeDocument recipe) {
        String prompt = buildPrompt(recipe);
        try {
            String rawResponse = chatBotApiService.request(prompt);
            return extractText(rawResponse);
        } catch (Exception e) {
            log.error("[guide] Gemini call failed for {}: {}", recipe.getName(), e.getMessage());
            return null;
        }
    }

    private String buildPrompt(RecipeDocument recipe) {
        return """
                {
                  "contents": [{
                    "parts": [{
                      "text": "You are a professional chef writing a cooking guide for home cooks. Write a clear, step-by-step how-to-cook guide for the following recipe.\\n\\nRecipe name: %s\\n\\nIngredients:\\n%s\\n\\nInstructions:\\n- Write numbered steps (1, 2, 3...).\\n- Each step should be concise and actionable.\\n- Include approximate time and temperature where relevant.\\n- End with a serving suggestion.\\n- Do not include any JSON, just plain readable text."
                    }]
                  }],
                  "generationConfig": {
                    "temperature": 0.7
                  }
                }
                """.formatted(
                sanitize(recipe.getName()),
                sanitize(recipe.getIngredients())
        );
    }

    // Extract candidates[0].content.parts[0].text from Gemini response
    private String extractText(String rawResponse) {
        if (rawResponse == null) return null;
        try {
            JsonNode root = objectMapper.readTree(rawResponse);
            return root.path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text")
                    .asText(null);
        } catch (Exception e) {
            log.error("[guide] failed to parse Gemini response: {}", e.getMessage());
            return null;
        }
    }

    private String sanitize(String value) {
        if (value == null) return "";
        // Escape quotes and newlines for embedding in JSON string
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "");
    }
}
