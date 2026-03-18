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

    /** Festival meal dedup: one recipe per festival-day per country. */
    Optional<RecipeCacheEntity> findBySourceAndCountryIgnoreCaseAndDatePublished(String source, String country, String datePublished);

    /** Last N festival suggestions for a country — used to avoid Gemini repetition. */
    List<RecipeCacheEntity> findTop5BySourceAndCountryIgnoreCaseOrderByCachedAtDesc(String source, String country);

    /** Fallback lookup when a duplicate name_normalized conflict occurs on save. */
    Optional<RecipeCacheEntity> findByNameNormalizedAndCountryIgnoreCase(String nameNormalized, String country);

    long countByCountryIgnoreCaseAndExcludedFalse(String country);
}
