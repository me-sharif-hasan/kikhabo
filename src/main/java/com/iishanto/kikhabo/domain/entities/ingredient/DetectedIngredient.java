package com.iishanto.kikhabo.domain.entities.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetectedIngredient {
    private String name;
    private String estimatedQuantity;
    private double confidence;
}
