package com.iishanto.kikhabo.domain.usercase.meal;

import com.iishanto.kikhabo.common.exception.global.GlobalServerException;
import com.iishanto.kikhabo.domain.datasource.MealDataSource;
import com.iishanto.kikhabo.domain.entities.meal.MealHistory;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@AllArgsConstructor
public class MealHistoryUpdateUserCase implements UseCase <Boolean, List<MealHistory>> {
    MealDataSource mealDataSource;

    @Override
    public Boolean execute(List<MealHistory> mealHistories) {
        mealHistories.forEach(mealHistory -> {
           if(!mealDataSource.update(mealHistory)){
               throw new GlobalServerException("Meal history update failed");
           }
        });
        return true;
    }
}
