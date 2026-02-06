package com.iishanto.kikhabo.web.dto.meal;

import com.iishanto.kikhabo.domain.entities.meal.Grocery;
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
public class MealHistoryDto {
    private Long id;
    private String mealStatus;
    private String mealName;
    private Float totalEnergy;
    private List<String> groceryNames;
    private Long timestamp;
    private Float rating;
    private String userNote;

    public static MealHistoryDto fromDomain(MealHistory mealHistory) {
        return MealHistoryDto.builder()
                .id(mealHistory.getId())
                .mealStatus(mealHistory.getMealStatus())
                .mealName(mealHistory.getMeal() != null ? mealHistory.getMeal().getMealName() : null)
                .totalEnergy(mealHistory.getMeal() != null ? mealHistory.getMeal().getTotalEnergy() : null)
                .groceryNames(mealHistory.getGroceries() != null ? mealHistory.getGroceries().stream()
                        .map(Grocery::getName)
                        .collect(Collectors.toList()) : null)
                .timestamp(mealHistory.getTimestamp())
                .rating(mealHistory.getRating())
                .userNote(mealHistory.getUserNote())
                .build();
    }
}
