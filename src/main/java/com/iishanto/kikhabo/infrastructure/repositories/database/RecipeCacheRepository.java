package com.iishanto.kikhabo.infrastructure.repositories.database;

import com.iishanto.kikhabo.infrastructure.model.RecipeCacheEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeCacheRepository extends JpaRepository<RecipeCacheEntity, Long> {

    Optional<RecipeCacheEntity> findByRecipeId(String recipeId);

    /** Composite-key lookup: one MongoDB recipe → one entry per country. */
    Optional<RecipeCacheEntity> findByMongoIdAndCountryIgnoreCase(String mongoId, String country);

    List<RecipeCacheEntity> findByCountryIgnoreCaseAndExcludedFalse(String country, Pageable pageable);

    long countByCountryIgnoreCaseAndExcludedFalse(String country);
}
