package com.iishanto.kikhabo.web.dto.meal;

import com.iishanto.kikhabo.domain.entities.text.MealPreferenceData;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MealPreferenceDto {
    @Max(10)
    @Min(0)
    private Float spicyRating;
    @Max(10)
    @Min(0)
    private Float saltRating;
    @Max(7)
    @Min(0)
    private Float dayCount;
    @Max(10)
    @Min(0)
    private Float priceRating;
    @Max(9)
    @Min(1)
    private Integer totalMealCount;
    @Max(3)
    @Min(1)
    private Integer mealPerDay;
    private List<Float> agesOfTheMembers;
    private List<AvailableIngredientDto> availableIngredients;

    public MealPreferenceData toDomain() {
        List<com.iishanto.kikhabo.domain.entities.meal.AvailableIngredient> domainIngredients =
                availableIngredients == null ? null :
                availableIngredients.stream()
                        .filter(Objects::nonNull)
                        .map(AvailableIngredientDto::toDomain)
                        .toList();
        return new MealPreferenceData(spicyRating, saltRating, dayCount, priceRating,
                totalMealCount, agesOfTheMembers, mealPerDay, domainIngredients);
    }
}
