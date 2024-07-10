package com.iishanto.kikhabo.web.dto.meal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealRatingStatusDto {
    @NotEmpty
    @Min(0)
    private Long id;
    private Float rating;
    private String userNote;
    private String mealStatus;
}
