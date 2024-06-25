package com.iishanto.captionmaker.infrastructure.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.captionmaker.domain.datasource.ChatBotDataSource;
import com.iishanto.captionmaker.domain.entities.text.Prompt;
import com.iishanto.captionmaker.domain.entities.text.PromptResponse;
import com.iishanto.captionmaker.infrastructure.external.ai.ChatBotApi;
import com.iishanto.captionmaker.infrastructure.model.AiPromptModel;
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
    public PromptResponse prompt(Prompt prompt) throws JsonProcessingException {
        setPromptMessage(prompt);
        AiPromptModel aiPromptModel=new AiPromptModel(prompt);
        String bardResponse = chatBotApi.request(aiPromptModel.getBardPrompt());
        JsonNode bardJson = objectMapper.readTree(bardResponse);
        JsonNode myOutput = bardJson.get("candidates").get(0).get("content").get("parts").get(0).get("text");
        String willBeOurServerResponse=myOutput.asText();

        willBeOurServerResponse=willBeOurServerResponse.substring(willBeOurServerResponse.indexOf("{"),willBeOurServerResponse.lastIndexOf("}")+1);
        logger.debug(STR."GEMINI RESPONED :- \{willBeOurServerResponse}");
        JsonNode ourServerResponseNode=objectMapper.readTree(willBeOurServerResponse);
        PromptResponse promptResponse=new PromptResponse();
        promptResponse.setStatus(ourServerResponseNode.get("status").asText());
        promptResponse.setError(ourServerResponseNode.get("error").asText());
        promptResponse.setCount(ourServerResponseNode.get("count").asInt());
        List <PromptResponse.ImageData> images=objectMapper.convertValue(ourServerResponseNode.get("images"), new TypeReference<>() {
        });
        promptResponse.setImages(images);

        return promptResponse;
    }

    private void setPromptMessage(Prompt prompt){
        prompt.setMessage(
                """
                    System Seed: %d
                    Suppose you are a api server. you are provided %d images. You have to give each of them
                    * a <title> with max 20 words non empty,
                    * a <description> with max 100 words non empty,
                    * some <tags> which is a list with max size 50. each tag is maximum 3 words.
                    
                    As a api server, you must output json. You are not allowed to say any extra text other than json. The suitable json format
                    is
                    {
                         status: <success>|<error>,
                         count: <number of images you processed>,
                         error: <message when you produced an error>,
                         images: [
                            {
                                id: <serial of the image>,
                                title: <title for the image>,
                                description: <description for the image>,
                                tags: [
                                    <tag 1>,
                                    <tag 2>,
                                    ......,
                                    <tag n>
                                ]
                            },
                            ....
                         ]
                    }
                    
                    keep in mind that keys and values should be double quoted.
                    """.formatted(System.currentTimeMillis(),prompt.getPictures().size()));
    }
}
