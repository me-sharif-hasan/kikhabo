package com.iishanto.captionmaker.infrastructure.data;

import com.iishanto.captionmaker.domain.datasource.AiDataProvider;
import com.iishanto.captionmaker.domain.entities.text.Prompt;
import com.iishanto.captionmaker.domain.entities.text.PromptResponse;
import org.springframework.stereotype.Component;

@Component
public class AiDataProviderImpl implements AiDataProvider {
    @Override
    public PromptResponse prompt(Prompt prompt) {
        return null;
    }
}
