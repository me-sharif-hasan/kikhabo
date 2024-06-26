package com.iishanto.kikhabo.domain.entities.text;

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
public class GroceryPlanningPromptResponse extends SchemaEntity {
    private String status;
    private String message;
    private GroceryData data;
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class GroceryData extends SchemaEntity {
        private int totalMeals;
        private List<Meals> meals;
        @EqualsAndHashCode(callSuper = true)
        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        final public static class Meals extends SchemaEntity{
            String mealName;
            Float totalEnergy;
            String note;
            String ingredients;
            List<Groceries> groceries;
            @EqualsAndHashCode(callSuper = true)
            @AllArgsConstructor
            @NoArgsConstructor
            @Data
            public static class Groceries extends SchemaEntity{
                Integer total;
                List <Grocery> itemList;
                @EqualsAndHashCode(callSuper = true)
                @AllArgsConstructor
                @NoArgsConstructor
                @Data
                public static class Grocery extends SchemaEntity{
                    String name;
                    String priceRatingOutOf10;
                    String amountInGm;
                }
            }
        }
    }
}
