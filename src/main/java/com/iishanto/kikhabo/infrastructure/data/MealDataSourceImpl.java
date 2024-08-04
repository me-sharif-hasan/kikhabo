package com.iishanto.kikhabo.infrastructure.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iishanto.kikhabo.domain.datasource.MealDataSource;
import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.domain.entities.meal.Meal;
import com.iishanto.kikhabo.domain.entities.meal.MealHistory;
import com.iishanto.kikhabo.domain.entities.people.User;
import com.iishanto.kikhabo.infrastructure.model.GroceryEntity;
import com.iishanto.kikhabo.infrastructure.model.MealEntity;
import com.iishanto.kikhabo.infrastructure.model.MealHistoryEntity;
import com.iishanto.kikhabo.infrastructure.model.UserEntity;
import com.iishanto.kikhabo.infrastructure.repositories.database.MealHistoryRepository;
import com.iishanto.kikhabo.infrastructure.repositories.database.MealRepository;
import com.iishanto.kikhabo.infrastructure.repositories.database.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
public class MealDataSourceImpl implements MealDataSource {
    MealRepository mealRepository;
    UserDataSource userDataSource;
    UserRepository userRepository;
    ObjectMapper objectMapper;
    MealHistoryRepository mealHistoryRepository;
    Logger logger;

    @Override
    public void addMeals(List<Meal> meals) {
        List <MealEntity> mealEntities=meals.stream().map(MealEntity::fromDomain).toList();
        mealRepository.saveAll(mealEntities);
    }

    @Override
    public void addMealHistory(List<Meal> meals) {
        List<MealHistoryEntity> mealHistoryEntities=new ArrayList<>();
        UserEntity user=userRepository.findByEmail(userDataSource.getAuthenticatedUser().getEmail());
        UUID groupId=UUID.randomUUID();
        meals.forEach(meal -> {
            MealHistoryEntity mealHistoryEntity=new MealHistoryEntity();
            MealEntity mealEntity=MealEntity.fromDomain(meal);
            mealHistoryEntity.setMealEntity(mealEntity);
            mealHistoryEntity.setTimestamp(System.currentTimeMillis());
            mealHistoryEntity.setGroceries(meal.getGroceries().stream().map(GroceryEntity::fromDomain).toList());
            mealHistoryEntity.setUser(user);
            mealHistoryEntity.setGroupId(groupId);
            mealEntity= mealRepository.save(mealEntity);
            meal.setId(mealEntity.getId());
            mealHistoryEntities.add(mealHistoryEntity);
        });
        user.setMealHistories(mealHistoryEntities);
        userRepository.save(user);
    }

    @Override
    public List<Meal> getLastMeals(int limit) {
        User user =userDataSource.getAuthenticatedUser();
        UserEntity userEntity=objectMapper.convertValue(user,UserEntity.class);
        return getMealsForUid(userEntity,limit);
    }

    @Override
    public boolean update(MealHistory mealHistory) {
        try{
            Long id=mealHistory.getId();
            MealHistoryEntity mealHistoryEntity=mealHistoryRepository.findById(id).orElse(null);
            if (mealHistoryEntity!=null){
                mealHistoryEntity.setRating(mealHistory.getRating());
                mealHistoryEntity.setUserNote(mealHistory.getUserNote());
                mealHistoryEntity.setMealStatus(mealHistory.getMealStatus());
                mealHistoryRepository.save(mealHistoryEntity);
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

    private List<Meal> getMealsForUid(UserEntity user, int limit) {
        List<MealHistoryEntity> mealHistoryEntities = mealHistoryRepository.findAllByUserOrderByTimestampDesc(user.getId(),limit);
        logger.info("MealHistoriesFetched. Total history {}",mealHistoryEntities.size());
        return mealHistoryEntities.stream().map(mealHistoryEntity -> mealHistoryEntity.getMealEntity().toDomain()).toList();
    }


}
