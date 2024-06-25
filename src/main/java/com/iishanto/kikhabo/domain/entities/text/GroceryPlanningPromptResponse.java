package com.iishanto.kikhabo.domain.entities.text;

import com.iishanto.kikhabo.domain.entities.common.SchemaEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GroceryPlanningPromptResponse extends SchemaEntity {
    private String status;
    private String error;
    private int count;
    Grocery grocery;
    @AllArgsConstructor
    @NoArgsConstructor
    final public static class Grocery extends SchemaEntity {
        String name;
        String season;
        Float priceRating;
        Float requiredAmountInGm;
        String specialNote;
        GroceryPlanningPromptResponse groceryPlanningPromptResponse;
    }

}
