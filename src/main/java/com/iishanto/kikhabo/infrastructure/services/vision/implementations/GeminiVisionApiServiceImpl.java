package com.iishanto.kikhabo.infrastructure.services.vision.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iishanto.kikhabo.infrastructure.config.GeminiVisionProperties;
import com.iishanto.kikhabo.infrastructure.services.vision.GeminiVisionApiService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.Base64;

@Component
public class GeminiVisionApiServiceImpl implements GeminiVisionApiService {

    private final RestClient restClient;
    private final String endpointWithKey;
    private final ObjectMapper objectMapper;
    private final Logger logger;

    public GeminiVisionApiServiceImpl(
            @Value("${google.gemini.apikey}") String defaultApiKey,
            @Value("${google.gemini.vision.apikey:}") String visionApiKey,
            GeminiVisionProperties properties,
            RestClient restClient,
            ObjectMapper objectMapper,
            Logger logger) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.logger = logger;
        boolean usingDedicated = !visionApiKey.isBlank();
        String resolvedKey = usingDedicated ? visionApiKey : defaultApiKey;
        this.endpointWithKey = properties.getEndpoint() + "?key=" + resolvedKey;
        // Log at startup so you can confirm key selection and model without restarting
        logger.info("[GeminiVision] Initialised — model endpoint : {}", properties.getEndpoint());
        logger.info("[GeminiVision] API key source             : {}",
                usingDedicated ? "dedicated vision key (GEMINI_VISION_KEY)" : "shared fallback key (GEMINI_KEY)");
    }

    @Override
    public String analyzeImage(byte[] imageBytes, String mimeType, String prompt) throws Exception {
        String requestBody = buildRequestBody(imageBytes, mimeType, prompt);
        logger.info("[GeminiVision] Sending request — image size: {} bytes, mime: {}", imageBytes.length, mimeType);
        String response = restClient.post()
                .uri(new URI(endpointWithKey))
                .body(requestBody)
                .retrieve()
                .body(String.class);
        logger.info("[GeminiVision] Response received — {} chars", response == null ? 0 : response.length());
        return response;
    }

    private String buildRequestBody(byte[] imageBytes, String mimeType, String prompt) throws Exception {
        ObjectNode root = objectMapper.createObjectNode();

        ArrayNode contents = root.putArray("contents");
        ObjectNode content = contents.addObject();
        ArrayNode parts = content.putArray("parts");

        // Inline image part
        ObjectNode inlineData = parts.addObject().putObject("inline_data");
        inlineData.put("mime_type", mimeType);
        inlineData.put("data", Base64.getEncoder().encodeToString(imageBytes));

        // Text prompt part
        parts.addObject().put("text", prompt);

        // Ask Gemini to return JSON directly so no markdown stripping is needed
        ObjectNode genConfig = root.putObject("generationConfig");
        genConfig.put("temperature", 0.2);
        genConfig.put("responseMimeType", "application/json");

        return objectMapper.writeValueAsString(root);
    }
}
