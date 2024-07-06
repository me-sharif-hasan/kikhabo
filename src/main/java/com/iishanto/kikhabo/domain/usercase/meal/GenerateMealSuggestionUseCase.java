package com.iishanto.kikhabo.domain.usercase.meal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.datasource.ChatBotDataSource;
import com.iishanto.kikhabo.domain.datasource.MealDataSource;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.meal.Grocery;
import com.iishanto.kikhabo.domain.entities.meal.Meal;
import com.iishanto.kikhabo.domain.entities.text.GroceryPlanningPromptResponse;
import com.iishanto.kikhabo.domain.entities.text.MealPreferenceData;
import com.iishanto.kikhabo.domain.entities.text.Prompt;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GenerateMealSuggestionUseCase implements UseCase<GroceryPlanningPromptResponse, MealPreferenceData> {
    ChatBotDataSource chatBotDataSource;
    UserDataSource userDataSource;
    MealDataSource mealDataSource;
    ObjectMapper objectMapper;

    @Override
    public GroceryPlanningPromptResponse execute(MealPreferenceData mealPreferenceData) throws JsonProcessingException {
        Prompt prompt=new Prompt();
        prompt.setMealPreferenceData(mealPreferenceData);
        GroceryPlanningPromptResponse promptResponse=objectMapper.readValue(chatBotDataSource.prompt(prompt),GroceryPlanningPromptResponse.class);
        List<Meal> meals=promptResponse.getData().getMeals();
        mealDataSource.addMealHistory(meals);

        return promptResponse;
    }
}
