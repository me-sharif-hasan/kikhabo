package com.iishanto.kikhabo.domain.usercase.meal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.datasource.ChatBotDataSource;
import com.iishanto.kikhabo.domain.entities.text.GroceryPlanningPromptResponse;
import com.iishanto.kikhabo.domain.entities.text.MealPreferenceData;
import com.iishanto.kikhabo.domain.entities.text.Prompt;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
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
