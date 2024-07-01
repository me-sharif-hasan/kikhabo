package com.iishanto.kikhabo.web.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.kikhabo.domain.entities.text.GroceryPlanningPromptResponse;
import com.iishanto.kikhabo.domain.usercase.meal.GenerateMealSuggestionUseCase;
import com.iishanto.kikhabo.web.dto.meal.MealPreferenceDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/meal-planning")
@Tag(name = "Meal Planning", description = "Use this API to create meal planning by providing your preferences.")
public class MealPlanController {
    GenerateMealSuggestionUseCase generateMealSuggestionUseCase;
    @PostMapping
    public ResponseEntity <GroceryPlanningPromptResponse> generateMealSuggestion(@Valid @RequestBody MealPreferenceDto mealPreference) throws JsonProcessingException {
        return new ResponseEntity<>(generateMealSuggestionUseCase.execute(mealPreference.toDomain()), HttpStatus.OK);
    }
}
