package com.iishanto.captionmaker.domain.datasource;

import com.iishanto.captionmaker.domain.entities.text.Prompt;
import com.iishanto.captionmaker.domain.entities.text.PromptResponse;

public interface AiDataProvider {
    public PromptResponse prompt(Prompt prompt);
}
