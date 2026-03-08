package com.iishanto.kikhabo.infrastructure.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GeminiVisionProperties.class)
public class GeminiVisionConfig {
}
