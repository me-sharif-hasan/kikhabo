package com.iishanto.kikhabo.domain.usercase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.kikhabo.domain.datasource.ChatBotDataSource;
import com.iishanto.kikhabo.domain.entities.text.PicturePromptResponse;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GenerateMealSuggestionUseCase implements UseCase<PicturePromptResponse, List<Object>> {
    ChatBotDataSource chatBotDataSource;

    @Override
    public PicturePromptResponse execute(List<Object> arguments) throws JsonProcessingException {
        return null;
    }
}
