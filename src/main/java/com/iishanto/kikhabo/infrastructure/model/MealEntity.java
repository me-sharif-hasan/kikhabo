package com.iishanto.kikhabo.infrastructure.model;

import com.iishanto.kikhabo.domain.entities.meal.Meal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "meals")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    String mealName;
    Float totalEnergy;
    @Column(columnDefinition = "TEXT")
    String note;
    @Column(columnDefinition = "TEXT")
    String ingredients;

    public static MealEntity fromDomain(Meal meal){
        return MealEntity.builder()
                .mealName(meal.getMealName())
                .totalEnergy(meal.getTotalEnergy())
                .note(meal.getNote())
                .ingredients(meal.getIngredients()).build();
    }
}
