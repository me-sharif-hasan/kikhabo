package com.iishanto.kikhabo.web.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.kikhabo.domain.usercase.GenerateMealSuggestionUseCase;
import com.iishanto.kikhabo.web.dto.MealPreferenceDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/meal-planning")
public class MealPlanController {
    GenerateMealSuggestionUseCase generateMealSuggestionUseCase;
    @PostMapping
    public String generateMealSuggestion(@RequestBody MealPreferenceDto mealPreferenceDto) throws JsonProcessingException {
        generateMealSuggestionUseCase.execute(mealPreferenceDto.toDomain());
        return "generateMealSuggestionUseCase.execute();";
    }
}
