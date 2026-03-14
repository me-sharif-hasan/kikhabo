package com.iishanto.kikhabo.infrastructure.services.config;

import com.iishanto.kikhabo.infrastructure.repositories.database.SystemConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Executes a Gemini API call with automatic key rotation.
 *
 * Key priority order:
 *   1. DB keys for the given group (ordered by priority ASC)
 *   2. Properties-file key as final fallback
 *
 * A key is rotated when it throws any exception (covers 401 invalid key,
 * 429 quota exceeded, network errors, etc.).
 */
@Service
public class GeminiKeyService {

    private static final Logger log = LoggerFactory.getLogger(GeminiKeyService.class);

    public static final String GROUP_TEXT   = "gemini_text_key";
    public static final String GROUP_VISION = "gemini_vision_key";

    private final SystemConfigRepository configRepository;
    private final RestClient restClient;

    public GeminiKeyService(SystemConfigRepository configRepository, RestClient restClient) {
        this.configRepository = configRepository;
        this.restClient = restClient;
    }

    /**
     * Executes a POST request to the Gemini endpoint, rotating through all available keys.
     *
     * @param baseEndpoint   endpoint URL without the ?key= param
     * @param requestBody    JSON body to send
     * @param configGroup    {@link #GROUP_TEXT} or {@link #GROUP_VISION}
     * @param propertiesKey  fallback key from application.properties (may be null/blank)
     * @return raw response string, or null if all keys exhausted
     */
    public String execute(String baseEndpoint, String requestBody, String configGroup, String propertiesKey) {
        List<String> keys = buildKeyList(configGroup, propertiesKey);

        if (keys.isEmpty()) {
            log.error("[GeminiKeyService] No keys available for group: {}", configGroup);
            return null;
        }

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            try {
                String response = restClient.post()
                        .uri(new URI(baseEndpoint + "?key=" + key))
                        .body(requestBody)
                        .retrieve()
                        .body(String.class);

                if (i > 0) {
                    log.info("[GeminiKeyService] Succeeded with key #{} for group {}", i + 1, configGroup);
                }
                return response;

            } catch (Exception e) {
                log.warn("[GeminiKeyService] Key #{} failed for group {}: {}", i + 1, configGroup, e.getMessage());
            }
        }

        log.error("[GeminiKeyService] All {} key(s) exhausted for group {}", keys.size(), configGroup);
        return null;
    }

    /** DB keys first (priority order), then properties key as last resort. */
    private List<String> buildKeyList(String configGroup, String propertiesKey) {
        List<String> keys = new ArrayList<>(
                configRepository.findByConfigGroupAndEnabledTrueOrderByPriorityAsc(configGroup)
                        .stream()
                        .map(c -> c.getConfigValue())
                        .toList()
        );
        // append properties key only if it's not already in the list
        if (propertiesKey != null && !propertiesKey.isBlank() && !keys.contains(propertiesKey)) {
            keys.add(propertiesKey);
        }
        return keys;
    }
}
