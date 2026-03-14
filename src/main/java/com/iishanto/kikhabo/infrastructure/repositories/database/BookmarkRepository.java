package com.iishanto.kikhabo.infrastructure.repositories.database;

import com.iishanto.kikhabo.infrastructure.model.BookmarkEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {

    boolean existsByUserIdAndRecipeId(Long userId, String recipeId);

    void deleteByUserIdAndRecipeId(Long userId, String recipeId);

    /** All recipe IDs bookmarked by user, newest first. */
    @Query("SELECT b.recipeId FROM BookmarkEntity b WHERE b.userId = :userId ORDER BY b.bookmarkedAt DESC")
    List<String> findRecipeIdsByUserId(@Param("userId") Long userId);

    /** Paginated recipe IDs — used when no search term is given. */
    @Query("SELECT b.recipeId FROM BookmarkEntity b WHERE b.userId = :userId ORDER BY b.bookmarkedAt DESC")
    Page<String> findRecipeIdsByUserIdPaged(@Param("userId") Long userId, Pageable pageable);

    long countByUserId(Long userId);
}
