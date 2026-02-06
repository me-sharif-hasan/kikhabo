package com.iishanto.kikhabo.domain.usercase.meal;

import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.meal.MealHistory;
import com.iishanto.kikhabo.infrastructure.model.MealHistoryEntity;
import com.iishanto.kikhabo.infrastructure.repositories.database.MealHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetMealHistoryUseCase {
        MealHistoryRepository mealHistoryRepository;
        UserDataSource userDataSource;

        public Page<MealHistory> execute(int page, int size) {
                Long userId = userDataSource.getAuthenticatedUser().getId();
                Pageable pageable = PageRequest.of(page, size);

                Page<MealHistoryEntity> entityPage = mealHistoryRepository.findAllByUserIdOrderByTimestampDesc(userId,
                                pageable);

                return entityPage.map(this::toDomain);
        }

        private MealHistory toDomain(MealHistoryEntity entity) {
                return MealHistory.builder()
                                .id(entity.getId())
                                .mealStatus(entity.getMealStatus())
                                .meal(entity.getMealEntity() != null ? entity.getMealEntity().toDomain() : null)
                                .groceries(entity.getGroceries() != null ? entity.getGroceries().stream()
                                                .map(g -> {
                                                        com.iishanto.kikhabo.domain.entities.meal.Grocery grocery = new com.iishanto.kikhabo.domain.entities.meal.Grocery();
                                                        grocery.setName(g.getName());
                                                        grocery.setPriceRatingOutOf10(g.getPriceRatingOutOf10());
                                                        grocery.setAmountInGm(g.getAmountInGm());
                                                        return grocery;
                                                })
                                                .collect(Collectors.toList()) : null)
                                .timestamp(entity.getTimestamp())
                                .rating(entity.getRating())
                                .userNote(entity.getUserNote())
                                .build();
        }
}
