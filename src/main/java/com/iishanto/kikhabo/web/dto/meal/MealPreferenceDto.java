package com.iishanto.kikhabo.web.dto.meal;

import com.iishanto.kikhabo.domain.entities.text.MealPreferenceData;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @NotNull
    @Max(9)
    @Min(1)
    private Integer totalMealCount;
    private List<Float> agesOfTheMembers;

    public MealPreferenceData toDomain(){
        return new MealPreferenceData(spicyRating,saltRating,dayCount,priceRating,totalMealCount,agesOfTheMembers);
    }
}
