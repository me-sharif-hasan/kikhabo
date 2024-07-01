package com.iishanto.kikhabo.infrastructure.model;

import com.iishanto.kikhabo.domain.entities.text.GroceryPlanningPromptResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Meal{
    @Id
    private Long id;
    private Long mealHistoryId;
    String mealName;
    Float totalEnergy;
    String note;
    String ingredients;
}
