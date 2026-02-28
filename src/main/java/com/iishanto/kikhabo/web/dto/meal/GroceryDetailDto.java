package com.iishanto.kikhabo.web.dto.meal;

import com.iishanto.kikhabo.domain.entities.meal.Grocery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroceryDetailDto {
    private String name;
    private String amountInGm;
    private String priceRatingOutOf10;

    public static GroceryDetailDto fromDomain(Grocery grocery) {
        return GroceryDetailDto.builder()
                .name(grocery.getName())
                .amountInGm(grocery.getAmountInGm())
                .priceRatingOutOf10(grocery.getPriceRatingOutOf10())
                .build();
    }
}
