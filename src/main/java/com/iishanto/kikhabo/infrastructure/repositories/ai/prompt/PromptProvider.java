package com.iishanto.kikhabo.infrastructure.repositories.ai.prompt;

import com.iishanto.kikhabo.domain.entities.text.Prompt;

public interface PromptProvider {
    String getPrompt(Prompt prompt);
}
