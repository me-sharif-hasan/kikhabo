package com.iishanto.captionmaker.infrastructure.external.ai.implementations;

import com.iishanto.captionmaker.infrastructure.external.ai.ChatBotApi;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@AllArgsConstructor
@Component
public class GoogleBardApiImpl implements ChatBotApi {
    RestClient apiClient;
    @Override
    public String request(String prompt) {
        return null;
    }
}
