package com.iishanto.kikhabo.domain.entities.meal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.iishanto.kikhabo.domain.entities.people.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MealHistory {
    private Long id;
    private String mealStatus;
    private Meal meal;
    private List<Grocery> groceries;
    private User user;
    private Long timestamp;
    private Float rating;
    private String userNote;
}
