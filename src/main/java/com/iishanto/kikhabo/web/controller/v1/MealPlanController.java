package com.iishanto.kikhabo.web.controller.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iishanto.kikhabo.domain.entities.meal.MealHistory;
import com.iishanto.kikhabo.domain.entities.text.GroceryPlanningPromptResponse;
import com.iishanto.kikhabo.domain.usercase.meal.GenerateMealSuggestionUseCase;
import com.iishanto.kikhabo.domain.usercase.meal.MealHistoryUpdateUserCase;
import com.iishanto.kikhabo.domain.usercase.preference.UpdatePreferenceUseCase;
import com.iishanto.kikhabo.domain.usercase.preference.command.in.UpdatePreferenceCommand;
import com.iishanto.kikhabo.web.dto.meal.MealPreferenceDto;
import com.iishanto.kikhabo.web.dto.meal.MealRatingStatusDto;
import com.iishanto.kikhabo.web.response.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/meal-planning")
@Tag(name = "Meal Planning", description = "Use this API to create meal planning by providing your preferences.")
public class MealPlanController {
    GenerateMealSuggestionUseCase generateMealSuggestionUseCase;
    MealHistoryUpdateUserCase mealHistoryUpdateUserCase;
    UpdatePreferenceUseCase updatePreferenceUseCase;
    @PostMapping
    public ResponseEntity <GroceryPlanningPromptResponse> generateMealSuggestion(@Valid @RequestBody MealPreferenceDto mealPreference) throws JsonProcessingException {
        return new ResponseEntity<>(generateMealSuggestionUseCase.execute(mealPreference.toDomain()), HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<SuccessResponse> acceptMeal(@RequestBody List<MealRatingStatusDto> mealRatingStatusDto) {
        List<MealHistory> mealHistories=mealRatingStatusDto.stream().map(ratingStatusDto-> MealHistory.builder()
                .id(ratingStatusDto.getId())
                .mealStatus(ratingStatusDto.getMealStatus())
                .userNote(ratingStatusDto.getUserNote())
                .rating(ratingStatusDto.getRating()).build()).toList();
        mealHistoryUpdateUserCase.execute(mealHistories);
        return new ResponseEntity<>(new SuccessResponse("success","Meal history updated"), HttpStatus.OK);
    }

    @PostMapping("update-preference")
    public ResponseEntity<SuccessResponse> updateMealPreference(@RequestBody UpdatePreferenceCommand updatePreferenceCommand) throws Exception {
        updatePreferenceUseCase.execute(updatePreferenceCommand);
        return new ResponseEntity<>(new SuccessResponse("success","Preference updated"), HttpStatus.OK);
    }
}
