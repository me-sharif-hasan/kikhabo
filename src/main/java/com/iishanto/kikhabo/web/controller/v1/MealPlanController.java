package com.iishanto.kikhabo.web.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.kikhabo.domain.entities.text.GroceryPlanningPromptResponse;
import com.iishanto.kikhabo.domain.usercase.GenerateMealSuggestionUseCase;
import com.iishanto.kikhabo.web.dto.MealPreferenceDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/meal-planning")
public class MealPlanController {
    GenerateMealSuggestionUseCase generateMealSuggestionUseCase;
    @PostMapping
    public ResponseEntity <GroceryPlanningPromptResponse> generateMealSuggestion(@Valid @RequestBody MealPreferenceDto mealPreference) throws JsonProcessingException {
        return new ResponseEntity<>(generateMealSuggestionUseCase.execute(mealPreference.toDomain()), HttpStatus.OK);
    }
}
