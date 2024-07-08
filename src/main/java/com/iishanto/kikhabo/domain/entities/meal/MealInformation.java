package com.iishanto.kikhabo.domain.entities.meal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iishanto.kikhabo.domain.entities.common.SchemaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MealInformation extends SchemaEntity {
    private int totalMeals;
    private List<Meal> meals;
}