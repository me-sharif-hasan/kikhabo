package com.iishanto.kikhabo.infrastructure.services.vision.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iishanto.kikhabo.infrastructure.config.GeminiVisionProperties;
import com.iishanto.kikhabo.infrastructure.services.config.GeminiKeyService;
import com.iishanto.kikhabo.infrastructure.services.vision.GeminiVisionApiService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class GeminiVisionApiServiceImpl implements GeminiVisionApiService {

    private final GeminiKeyService geminiKeyService;
    private final String endpoint;
    private final String propertiesKey;
    private final ObjectMapper objectMapper;
    private final Logger logger;

    public GeminiVisionApiServiceImpl(
            GeminiKeyService geminiKeyService,
            @Value("${google.gemini.apikey}") String defaultApiKey,
            @Value("${google.gemini.vision.apikey:}") String visionApiKey,
            GeminiVisionProperties properties,
            ObjectMapper objectMapper,
            Logger logger) {
        this.geminiKeyService = geminiKeyService;
        this.endpoint = properties.getEndpoint();
        this.objectMapper = objectMapper;
        this.logger = logger;
        // prefer dedicated vision key from properties, fall back to shared key
        this.propertiesKey = visionApiKey.isBlank() ? defaultApiKey : visionApiKey;
        logger.info("[GeminiVision] Initialised — endpoint: {}", endpoint);
    }

    @Override
    public String analyzeImage(byte[] imageBytes, String mimeType, String prompt) throws Exception {
        String requestBody = buildRequestBody(imageBytes, mimeType, prompt);
        logger.info("[GeminiVision] Sending request — {} bytes, mime: {}", imageBytes.length, mimeType);
        String response = geminiKeyService.execute(endpoint, requestBody, GeminiKeyService.GROUP_VISION, propertiesKey);
        logger.info("[GeminiVision] Response received — {} chars", response == null ? 0 : response.length());
        return response;
    }

    private String buildRequestBody(byte[] imageBytes, String mimeType, String prompt) throws Exception {
        ObjectNode root = objectMapper.createObjectNode();

        ArrayNode contents = root.putArray("contents");
        ObjectNode content = contents.addObject();
        ArrayNode parts = content.putArray("parts");

        ObjectNode inlineData = parts.addObject().putObject("inline_data");
        inlineData.put("mime_type", mimeType);
        inlineData.put("data", Base64.getEncoder().encodeToString(imageBytes));

        parts.addObject().put("text", prompt);

        ObjectNode genConfig = root.putObject("generationConfig");
        genConfig.put("temperature", 0.2);
        genConfig.put("responseMimeType", "application/json");

        return objectMapper.writeValueAsString(root);
    }
}
