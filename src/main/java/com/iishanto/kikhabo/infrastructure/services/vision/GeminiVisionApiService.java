package com.iishanto.kikhabo.infrastructure.services.vision;

/**
 * Abstraction over the Gemini Vision API.
 * Accepts raw image bytes and a text prompt, returns the raw API response.
 */
public interface GeminiVisionApiService {
    String analyzeImage(byte[] imageBytes, String mimeType, String prompt) throws Exception;
}
