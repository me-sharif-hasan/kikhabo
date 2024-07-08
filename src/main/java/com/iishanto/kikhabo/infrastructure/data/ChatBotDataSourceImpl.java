package com.iishanto.kikhabo.infrastructure.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.datasource.ChatBotDataSource;
import com.iishanto.kikhabo.domain.entities.text.Prompt;
import com.iishanto.kikhabo.infrastructure.services.chatbot.ChatBotApiService;
import com.iishanto.kikhabo.infrastructure.repositories.ai.prompt.PromptProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ChatBotDataSourceImpl implements ChatBotDataSource {
    ChatBotApiService chatBotApiService;
    ObjectMapper objectMapper;
    PromptProvider promptProvider;
    Logger logger;
    @Override
    public String prompt(Prompt prompt) throws JsonProcessingException {
        String bardResponse = chatBotApiService.request(promptProvider.getPrompt(prompt));
        logger.debug("Bard response: {}",bardResponse);
        JsonNode bardJson = objectMapper.readTree(bardResponse);
        JsonNode myOutput = bardJson.get("candidates").get(0).get("content").get("parts").get(0).get("text");
        String geminiResponse=myOutput.asText();
        logger.info("GEMINI Responded :- {}",geminiResponse);
        return geminiResponse.substring(geminiResponse.indexOf("{"),geminiResponse.lastIndexOf("}")+1);
    }
}
