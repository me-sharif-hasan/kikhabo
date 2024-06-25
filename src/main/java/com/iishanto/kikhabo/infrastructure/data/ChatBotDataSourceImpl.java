package com.iishanto.kikhabo.infrastructure.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.datasource.ChatBotDataSource;
import com.iishanto.kikhabo.domain.entities.text.Prompt;
import com.iishanto.kikhabo.domain.entities.text.PicturePromptResponse;
import com.iishanto.kikhabo.infrastructure.external.ai.ChatBotApi;
import com.iishanto.kikhabo.infrastructure.model.AiPromptModel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class ChatBotDataSourceImpl implements ChatBotDataSource {
    ChatBotApi chatBotApi;
    ObjectMapper objectMapper;
    Logger logger;
    @Override
    public PicturePromptResponse prompt(Prompt prompt) throws JsonProcessingException {
        AiPromptModel aiPromptModel=new AiPromptModel(prompt);
        String bardResponse = chatBotApi.request(aiPromptModel.getBardPrompt());
        JsonNode bardJson = objectMapper.readTree(bardResponse);
        JsonNode myOutput = bardJson.get("candidates").get(0).get("content").get("parts").get(0).get("text");
        String willBeOurServerResponse=myOutput.asText();

        willBeOurServerResponse=willBeOurServerResponse.substring(willBeOurServerResponse.indexOf("{"),willBeOurServerResponse.lastIndexOf("}")+1);
        logger.debug(STR."GEMINI RESPONED :- \{willBeOurServerResponse}");
        JsonNode ourServerResponseNode=objectMapper.readTree(willBeOurServerResponse);
        PicturePromptResponse picturePromptResponse =new PicturePromptResponse();
        picturePromptResponse.setStatus(ourServerResponseNode.get("status").asText());
        picturePromptResponse.setError(ourServerResponseNode.get("error").asText());
        picturePromptResponse.setCount(ourServerResponseNode.get("count").asInt());
        List <PicturePromptResponse.ImageData> images=objectMapper.convertValue(ourServerResponseNode.get("images"), new TypeReference<>() {
        });
        picturePromptResponse.setImages(images);

        return picturePromptResponse;
    }
}
