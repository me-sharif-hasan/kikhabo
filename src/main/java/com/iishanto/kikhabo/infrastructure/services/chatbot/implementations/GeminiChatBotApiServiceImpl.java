package com.iishanto.kikhabo.infrastructure.services.chatbot.implementations;

import com.iishanto.kikhabo.infrastructure.services.chatbot.ChatBotApiService;
import com.iishanto.kikhabo.infrastructure.services.config.GeminiKeyService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GeminiChatBotApiServiceImpl implements ChatBotApiService {

    private final GeminiKeyService geminiKeyService;
    private final String endpoint;
    private final String propertiesKey;
    private final Logger logger;

    public GeminiChatBotApiServiceImpl(
            GeminiKeyService geminiKeyService,
            @Value("${google.gemini.endpoint}") String endpoint,
            @Value("${google.gemini.apikey}") String propertiesKey,
            Logger logger) {
        this.geminiKeyService = geminiKeyService;
        this.endpoint = endpoint;
        this.propertiesKey = propertiesKey;
        this.logger = logger;
    }

    @Override
    public String request(String prompt) {
        logger.debug("[GeminiChat] Requesting endpoint: {}", endpoint);
        return geminiKeyService.execute(endpoint, prompt, GeminiKeyService.GROUP_TEXT, propertiesKey);
    }
}
