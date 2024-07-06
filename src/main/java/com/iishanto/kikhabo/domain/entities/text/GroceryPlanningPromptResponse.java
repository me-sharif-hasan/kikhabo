package com.iishanto.kikhabo.domain.entities.text;

import com.iishanto.kikhabo.domain.entities.common.SchemaEntity;
import com.iishanto.kikhabo.domain.entities.meal.MealInformation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroceryPlanningPromptResponse extends SchemaEntity {
    private String status;
    private String message;
    private MealInformation data;
}
