package com.iishanto.kikhabo.web.dto.meal;

import com.iishanto.kikhabo.domain.entities.meal.AvailableIngredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableIngredientDto {
    private String name;
    private String quantity;

    public AvailableIngredient toDomain() {
        return new AvailableIngredient(name, quantity);
    }
}
