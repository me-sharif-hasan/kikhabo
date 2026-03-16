package com.iishanto.kikhabo.infrastructure.services.recipe;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iishanto.kikhabo.infrastructure.model.RecipeCacheEntity;
import com.iishanto.kikhabo.infrastructure.services.config.GeminiKeyService;
import com.iishanto.kikhabo.infrastructure.services.storage.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class RecipeImageService {

    private static final Logger log = LoggerFactory.getLogger(RecipeImageService.class);

    private static final int TARGET_WIDTH  = 300;
    private static final int TARGET_HEIGHT = 256;

    private final GeminiKeyService geminiKeyService;
    private final S3Service s3Service;
    private final ObjectMapper objectMapper;
    private final String imageEndpoint;
    private final String imageApiKey;

    public RecipeImageService(GeminiKeyService geminiKeyService,
                               S3Service s3Service,
                               ObjectMapper objectMapper,
                               @Value("${google.gemini.image.endpoint}") String imageEndpoint,
                               @Value("${google.gemini.image.apikey:}") String imageApiKey) {
        this.geminiKeyService = geminiKeyService;
        this.s3Service = s3Service;
        this.objectMapper = objectMapper;
        this.imageEndpoint = imageEndpoint;
        this.imageApiKey = imageApiKey;
    }

    /**
     * Generates a hyperrealistic 300×256 food photo for the recipe using Gemini image generation,
     * resizes it, uploads to S3, and returns the public URL.
     *
     * Triggered whenever a new recipe is persisted to the MySQL cache from a MongoDB seed.
     * Returns null on any failure — image is treated as optional by the caller.
     */
    public String generateAndUpload(RecipeCacheEntity recipe) {
        try {
            String prompt = buildImagePrompt(recipe);
            String requestBody = buildRequestBody(prompt);

            log.info("[RecipeImage] Requesting image generation for '{}'", recipe.getName());
            String response = geminiKeyService.execute(
                    imageEndpoint, requestBody, GeminiKeyService.GROUP_VISION, imageApiKey);

            if (response == null) {
                log.warn("[RecipeImage] Gemini returned null for recipe='{}'", recipe.getName());
                return null;
            }

            byte[] imageBytes = extractAndResize(response);
            if (imageBytes == null) return null;

            String url = s3Service.uploadBytes(imageBytes, "image/png", "recipe-images", ".png");
            log.info("[RecipeImage] Uploaded image for '{}' → {}", recipe.getName(), url);
            return url;

        } catch (Exception e) {
            log.error("[RecipeImage] Failed for '{}': {}", recipe.getName(), e.getMessage());
            return null;
        }
    }

    // ── Prompt ───────────────────────────────────────────────────────────────

    private String buildImagePrompt(RecipeCacheEntity recipe) {
        String name        = recipe.getName()        != null ? recipe.getName()        : "traditional dish";
        String country     = recipe.getCountry()     != null ? recipe.getCountry()     : "local";
        String description = recipe.getDescription() != null ? recipe.getDescription() : "";

        return "Hyperrealistic professional food photography of " + name
                + ", a traditional " + country + " dish"
                + (description.isBlank() ? "" : ". " + description)
                + ". Plated on authentic " + country + " serving ware, beautifully garnished."
                + " Slightly angled top-down view, warm studio lighting, ultra sharp focus,"
                + " rich vibrant colours, 4K food photography, magazine quality.";
    }

    // ── Request body ─────────────────────────────────────────────────────────

    private String buildRequestBody(String prompt) throws Exception {
        ObjectNode root = objectMapper.createObjectNode();
        ArrayNode contents = root.putArray("contents");
        ArrayNode parts = contents.addObject().putArray("parts");
        parts.addObject().put("text", prompt);

        // Tell Gemini we want an image back, not text
        root.putObject("generationConfig")
                .putArray("responseModalities")
                .add("IMAGE");

        return objectMapper.writeValueAsString(root);
    }

    // ── Response parsing + resize ─────────────────────────────────────────────

    private byte[] extractAndResize(String rawResponse) {
        try {
            JsonNode root = objectMapper.readTree(rawResponse);
            JsonNode parts = root.path("candidates").path(0).path("content").path("parts");

            for (JsonNode part : parts) {
                JsonNode inlineData = part.path("inlineData");
                if (!inlineData.isMissingNode()) {
                    byte[] raw = Base64.getDecoder().decode(inlineData.path("data").asText());
                    return resizeTo300x256(raw);
                }
            }
            log.warn("[RecipeImage] No inlineData in Gemini response");
            return null;
        } catch (Exception e) {
            log.error("[RecipeImage] Failed to extract/resize image: {}", e.getMessage());
            return null;
        }
    }

    private byte[] resizeTo300x256(byte[] original) throws Exception {
        BufferedImage src = ImageIO.read(new ByteArrayInputStream(original));
        if (src == null) throw new IllegalArgumentException("Cannot decode image bytes from Gemini");

        BufferedImage out = new BufferedImage(TARGET_WIDTH, TARGET_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,     RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(src.getScaledInstance(TARGET_WIDTH, TARGET_HEIGHT, Image.SCALE_SMOOTH), 0, 0, null);
        g.dispose();

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ImageIO.write(out, "png", buf);
        return buf.toByteArray();
    }
}
