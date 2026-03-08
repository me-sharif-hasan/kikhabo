package com.iishanto.kikhabo.infrastructure.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.datasource.IngredientDetectionDataSource;
import com.iishanto.kikhabo.domain.entities.ingredient.DetectedIngredient;
import com.iishanto.kikhabo.infrastructure.config.GeminiVisionProperties;
import com.iishanto.kikhabo.infrastructure.services.image.ImageCompressionService;
import com.iishanto.kikhabo.infrastructure.services.vision.GeminiVisionApiService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class IngredientDetectionDataSourceImpl implements IngredientDetectionDataSource {

    private static final String INGREDIENT_DETECTION_PROMPT = """
            Analyze this image and identify all cooking ingredients that are visible.
            Return ONLY a JSON object matching this exact schema — no markdown, no explanation:
            {
              "ingredients": [
                {
                  "name": "ingredient name in English",
                  "estimatedQuantity": "estimated amount with unit (e.g. '2 cups', '500g', '3 pieces', '1 bunch')",
                  "confidence": 0.85
                }
              ]
            }
            Rules:
            - Only include actual cooking ingredients (vegetables, fruits, meat, seafood, spices, grains, oils, dairy, eggs, condiments, etc.)
            - Exclude utensils, containers, plates, cutlery, packaging, and non-food items.
            - confidence is a decimal from 0.0 to 1.0 representing your certainty that the item is a visible cooking ingredient.
            - estimatedQuantity should be a visual estimate based only on what is visible in the image.
            - name must always be in English.
            """;

    private final GeminiVisionApiService geminiVisionApiService;
    private final ImageCompressionService imageCompressionService;
    private final GeminiVisionProperties visionProperties;
    private final ObjectMapper objectMapper;
    private final Logger logger;

    @Override
    public List<DetectedIngredient> detectIngredients(byte[] imageBytes, String mimeType) throws Exception {
        // --- Step 1: compression ---
        logger.info("[IngredientDetection] Step 1/3 — compressing image | original: {} bytes, type: {}", imageBytes.length, mimeType);
        byte[] compressed = imageCompressionService.compressIfNeeded(imageBytes);
        if (compressed.length < imageBytes.length) {
            logger.info("[IngredientDetection] Image compressed: {} bytes -> {} bytes (saved {} bytes)",
                    imageBytes.length, compressed.length, imageBytes.length - compressed.length);
        } else {
            logger.info("[IngredientDetection] Image already within limits, no compression applied ({} bytes)", compressed.length);
        }

        // --- Step 2: Gemini Vision call ---
        logger.info("[IngredientDetection] Step 2/3 — calling Gemini Vision API");
        String geminiResponse = geminiVisionApiService.analyzeImage(compressed, "image/jpeg", INGREDIENT_DETECTION_PROMPT);

        String ingredientsJson = extractTextFromGeminiResponse(geminiResponse);
        logger.debug("[IngredientDetection] Raw ingredients JSON from Gemini: {}", ingredientsJson);

        // --- Step 3: parse & confidence filter ---
        logger.info("[IngredientDetection] Step 3/3 — parsing response and filtering by confidence >= {}",
                visionProperties.getConfidenceThreshold());
        List<DetectedIngredient> result = parseAndFilterByConfidence(ingredientsJson);
        logger.info("[IngredientDetection] Done — {} ingredient(s) returned to caller", result.size());
        return result;
    }

    private String extractTextFromGeminiResponse(String response) throws Exception {
        JsonNode root = objectMapper.readTree(response);
        return root.path("candidates").get(0)
                .path("content").path("parts").get(0)
                .path("text").asText();
    }

    private List<DetectedIngredient> parseAndFilterByConfidence(String json) throws Exception {
        JsonNode root = objectMapper.readTree(json);
        double threshold = visionProperties.getConfidenceThreshold();
        List<DetectedIngredient> result = new ArrayList<>();
        int skipped = 0;

        for (JsonNode node : root.path("ingredients")) {
            double confidence = node.path("confidence").asDouble(0.0);
            String name = node.path("name").asText();
            if (confidence >= threshold) {
                result.add(DetectedIngredient.builder()
                        .name(name)
                        .estimatedQuantity(node.path("estimatedQuantity").asText())
                        .confidence(confidence)
                        .build());
                logger.info("[IngredientDetection]   ACCEPTED  '{}' — confidence: {}", name, confidence);
            } else {
                logger.info("[IngredientDetection]   REJECTED  '{}' — confidence: {} (below {})", name, confidence, threshold);
                skipped++;
            }
        }

        logger.info("[IngredientDetection] Filter summary: {} accepted, {} rejected", result.size(), skipped);
        return result;
    }
}
