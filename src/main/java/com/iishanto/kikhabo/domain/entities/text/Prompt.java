package com.iishanto.kikhabo.domain.entities.text;

import com.iishanto.kikhabo.domain.entities.common.SchemaEntity;
import com.iishanto.kikhabo.domain.entities.meal.Meal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Prompt extends SchemaEntity {
    private String message;
    private MealPreferenceData mealPreferenceData;
    private List<Meal> lastMealRecord;
}
