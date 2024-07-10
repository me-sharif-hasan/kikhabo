package com.iishanto.kikhabo.domain.datasource;

import com.iishanto.kikhabo.domain.entities.meal.Grocery;
import com.iishanto.kikhabo.domain.entities.meal.Meal;
import com.iishanto.kikhabo.domain.entities.meal.MealHistory;
import com.iishanto.kikhabo.domain.entities.people.User;

import java.util.List;

public interface MealDataSource {
    void addMeals(List<Meal> meals);
    void addMealHistory(List<Meal> meals);

    List<Meal> getLastMeals(int limit);

    boolean update(MealHistory mealHistory);
}
