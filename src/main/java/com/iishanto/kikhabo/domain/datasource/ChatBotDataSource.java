package com.iishanto.kikhabo.domain.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.kikhabo.domain.entities.text.Prompt;

public interface ChatBotDataSource {
    String prompt(Prompt prompt) throws JsonProcessingException;
}
