package com.iishanto.kikhabo.web.dto;

import com.iishanto.kikhabo.domain.entities.text.MealPreferenceData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MealPreferenceDto {
    private Float spicyRating;
    private Float saltRating;
    private Float dayCount;
    private Float priceRating;
    private Integer totalMealCount;

    public MealPreferenceData toDomain(){
        System.out.println(spicyRating+"::::");
        return new MealPreferenceData(spicyRating,saltRating,dayCount,priceRating,totalMealCount);
    }
}
