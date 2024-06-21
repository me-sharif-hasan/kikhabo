package com.iishanto.captionmaker.domain.usercase;

import com.iishanto.captionmaker.domain.datasource.AiDataProvider;
import com.iishanto.captionmaker.domain.entities.text.Prompt;
import com.iishanto.captionmaker.domain.entities.text.PromptResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetPictureTitleAndDescriptionWithTagUseCase implements UseCase <Prompt, PromptResponse> {
    AiDataProvider aiDataProvider;

    @Override
    public PromptResponse execute(Prompt arguments) {
        return null;
    }
}
