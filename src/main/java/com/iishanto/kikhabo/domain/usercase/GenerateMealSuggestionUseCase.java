package com.iishanto.kikhabo.domain.usercase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.datasource.ChatBotDataSource;
import com.iishanto.kikhabo.domain.entities.text.GroceryPlanningPromptResponse;
import com.iishanto.kikhabo.domain.entities.text.MealPreferenceData;
import com.iishanto.kikhabo.domain.entities.text.Prompt;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GenerateMealSuggestionUseCase implements UseCase<GroceryPlanningPromptResponse, MealPreferenceData> {
    ChatBotDataSource chatBotDataSource;
    ObjectMapper objectMapper;

    @Override
    public GroceryPlanningPromptResponse execute(MealPreferenceData mealPreferenceData) throws JsonProcessingException {
        Prompt prompt=new Prompt();
        prompt.setMealPreferenceData(mealPreferenceData);

        String getData=getRT();

        return objectMapper.readValue(chatBotDataSource.prompt(prompt),GroceryPlanningPromptResponse.class);
    }


    private <RT> RT getRT(){
        return (RT) "new Object()";
    }
}
