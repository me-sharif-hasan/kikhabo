package com.iishanto.kikhabo.domain.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.kikhabo.domain.entities.text.Prompt;
import com.iishanto.kikhabo.domain.entities.text.PicturePromptResponse;

public interface ChatBotDataSource {
    PicturePromptResponse prompt(Prompt prompt) throws JsonProcessingException;
}
