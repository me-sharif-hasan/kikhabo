package com.iishanto.kikhabo.infrastructure.model;

import com.iishanto.kikhabo.domain.entities.meal.Grocery;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "groceries",indexes = {
        @Index(name = "unk_name",columnList = "name")
})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroceryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    String name;
    String priceRatingOutOf10;
    String amountInGm;

    public static GroceryEntity fromDomain(Grocery grocery){
        return GroceryEntity.builder()
                .name(grocery.getName())
                .priceRatingOutOf10(grocery.getPriceRatingOutOf10())
                .amountInGm(grocery.getAmountInGm())
                .build();
    }
}
