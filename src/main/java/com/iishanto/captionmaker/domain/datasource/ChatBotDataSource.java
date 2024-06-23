package com.iishanto.captionmaker.domain.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.captionmaker.domain.entities.text.Prompt;
import com.iishanto.captionmaker.domain.entities.text.PromptResponse;

public interface ChatBotDataSource {
    PromptResponse prompt(Prompt prompt) throws JsonProcessingException;
}
