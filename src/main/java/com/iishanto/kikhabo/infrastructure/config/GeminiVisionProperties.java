package com.iishanto.kikhabo.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "google.gemini.vision")
public class GeminiVisionProperties {
    private String endpoint;
    private long maxImageSizeBytes = 1_048_576; // 1 MB default
    private int maxImageDimension = 1024;        // px, longest edge
    private double confidenceThreshold = 0.70;
}
