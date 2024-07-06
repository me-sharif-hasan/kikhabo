package com.iishanto.kikhabo.infrastructure.services.chatbot.implementations;

import com.iishanto.kikhabo.infrastructure.services.chatbot.ChatBotApiService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Component
public class GeminiChatBotApiServiceImpl implements ChatBotApiService {
    final private RestClient apiClient;
    final private String endpoint;
    Logger logger;
    public GeminiChatBotApiServiceImpl(@Value("${google.gemini.apikey}") String apiKey, @Value("${google.gemini.endpoint}") String endpoint, RestClient restClient, Logger logger){
        this.apiClient=restClient;
        this.endpoint=endpoint+"?key="+apiKey;
        this.logger=logger;
    }

    @Override
    public String request(String prompt) {
        try {
            logger.debug("Requesting Google Gemini :- %s".formatted(endpoint));
            return apiClient.post().uri(new URI(endpoint)).body(prompt).retrieve().body(String.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
