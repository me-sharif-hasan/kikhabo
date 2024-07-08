package com.iishanto.kikhabo.infrastructure.repositories.database;

import com.iishanto.kikhabo.infrastructure.model.MealEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<MealEntity,Long> {
}
