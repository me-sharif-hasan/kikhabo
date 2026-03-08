package com.iishanto.kikhabo.domain.entities.meal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableIngredient {
    private String name;
    private String quantity;
}
