package com.iishanto.kikhabo.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "google.firebase")
public class FcmProperties {

    private String projectId;
    private String serviceAccountPath = "classpath:google-service.json";
}
