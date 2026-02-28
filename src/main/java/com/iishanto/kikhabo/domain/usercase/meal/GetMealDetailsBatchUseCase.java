package com.iishanto.kikhabo.domain.usercase.meal;

import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.meal.Grocery;
import com.iishanto.kikhabo.domain.entities.meal.MealHistory;
import com.iishanto.kikhabo.infrastructure.model.MealHistoryEntity;
import com.iishanto.kikhabo.infrastructure.repositories.database.MealHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetMealDetailsBatchUseCase {

    MealHistoryRepository mealHistoryRepository;
    UserDataSource userDataSource;

    public List<MealHistory> execute(List<Long> mealHistoryIds) {
        Long userId = userDataSource.getAuthenticatedUser().getId();
        List<MealHistoryEntity> entities = mealHistoryRepository.findAllByIdInAndUserId(mealHistoryIds, userId);
        return entities.stream().map(this::toDomain).collect(Collectors.toList());
    }

    private MealHistory toDomain(MealHistoryEntity entity) {
        return MealHistory.builder()
                .id(entity.getId())
                .mealStatus(entity.getMealStatus())
                .meal(entity.getMealEntity() != null ? entity.getMealEntity().toDomain() : null)
                .groceries(entity.getGroceries() != null
                        ? entity.getGroceries().stream()
                                .map(g -> {
                                    Grocery grocery = new Grocery();
                                    grocery.setName(g.getName());
                                    grocery.setPriceRatingOutOf10(g.getPriceRatingOutOf10());
                                    grocery.setAmountInGm(g.getAmountInGm());
                                    return grocery;
                                })
                                .collect(Collectors.toList())
                        : null)
                .timestamp(entity.getTimestamp())
                .rating(entity.getRating())
                .userNote(entity.getUserNote())
                .build();
    }
}
