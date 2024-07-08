package com.iishanto.kikhabo.infrastructure.repositories.database;

import com.iishanto.kikhabo.infrastructure.model.MealEntity;
import com.iishanto.kikhabo.infrastructure.model.MealHistoryEntity;
import com.iishanto.kikhabo.infrastructure.model.UserEntity;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MealHistoryRepository extends JpaRepository<MealHistoryEntity,Long> {
    @Query(nativeQuery = true,value = "select * from meal_histories where user_id=:user_id order by timestamp desc limit :limit")
    List <MealHistoryEntity> findAllByUserOrderByTimestampDesc(long user_id,int limit);
}
