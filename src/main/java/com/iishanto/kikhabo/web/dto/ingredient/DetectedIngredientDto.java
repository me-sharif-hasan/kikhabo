package com.iishanto.kikhabo.web.dto.ingredient;

import com.iishanto.kikhabo.domain.entities.ingredient.DetectedIngredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetectedIngredientDto {
    private String name;
    private String estimatedQuantity;
    private double confidence;

    public static DetectedIngredientDto fromDomain(DetectedIngredient domain) {
        return new DetectedIngredientDto(domain.getName(), domain.getEstimatedQuantity(), domain.getConfidence());
    }
}
