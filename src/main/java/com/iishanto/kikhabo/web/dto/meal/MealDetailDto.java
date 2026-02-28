package com.iishanto.kikhabo.web.dto.meal;

import com.iishanto.kikhabo.domain.entities.meal.MealHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealDetailDto {
    private Long mealHistoryId;
    private String mealStatus;
    private String mealName;
    private Float totalEnergy;
    private String note;
    private String ingredients;
    private List<GroceryDetailDto> groceries;
    private Long timestamp;
    private Float rating;
    private String userNote;

    public static MealDetailDto fromDomain(MealHistory mealHistory) {
        return MealDetailDto.builder()
                .mealHistoryId(mealHistory.getId())
                .mealStatus(mealHistory.getMealStatus())
                .mealName(mealHistory.getMeal() != null ? mealHistory.getMeal().getMealName() : null)
                .totalEnergy(mealHistory.getMeal() != null ? mealHistory.getMeal().getTotalEnergy() : null)
                .note(mealHistory.getMeal() != null ? mealHistory.getMeal().getNote() : null)
                .ingredients(mealHistory.getMeal() != null ? mealHistory.getMeal().getIngredients() : null)
                .groceries(mealHistory.getGroceries() != null
                        ? mealHistory.getGroceries().stream()
                                .map(GroceryDetailDto::fromDomain)
                                .collect(Collectors.toList())
                        : null)
                .timestamp(mealHistory.getTimestamp())
                .rating(mealHistory.getRating())
                .userNote(mealHistory.getUserNote())
                .build();
    }
}
