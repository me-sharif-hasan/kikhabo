package com.iishanto.kikhabo.domain.usercase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.kikhabo.domain.datasource.ChatBotDataSource;
import com.iishanto.kikhabo.domain.entities.text.MealPreferenceData;
import com.iishanto.kikhabo.domain.entities.text.PicturePromptResponse;
import com.iishanto.kikhabo.domain.entities.text.Prompt;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class GenerateMealSuggestionUseCase implements UseCase<PicturePromptResponse, MealPreferenceData> {
    ChatBotDataSource chatBotDataSource;

    @Override
    public PicturePromptResponse execute(MealPreferenceData mealPreferenceData) throws JsonProcessingException {
        Prompt prompt=new Prompt();
        prompt.setMealPreferenceData(mealPreferenceData);
        chatBotDataSource.prompt(prompt);
        return null;
    }
}
