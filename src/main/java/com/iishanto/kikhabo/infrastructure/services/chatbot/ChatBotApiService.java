package com.iishanto.kikhabo.infrastructure.services.chatbot;

import org.springframework.stereotype.Repository;

@Repository
public interface ChatBotApiService {
    public String request(String prompt);
}
