package com.iishanto.kikhabo.domain.usercase.recipe;

import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.infrastructure.model.BookmarkEntity;
import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import com.iishanto.kikhabo.infrastructure.repositories.database.BookmarkRepository;
import com.iishanto.kikhabo.infrastructure.repositories.database.RecipeCacheRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookmarkRecipeUseCase {

    private final BookmarkRepository bookmarkRepository;
    private final MongoTemplate mongoTemplate;
    private final UserDataSource userDataSource;
    private final RecipeCacheRepository recipeCacheRepository;

    public BookmarkRecipeUseCase(BookmarkRepository bookmarkRepository,
                                 MongoTemplate mongoTemplate,
                                 UserDataSource userDataSource,
                                 RecipeCacheRepository recipeCacheRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.mongoTemplate = mongoTemplate;
        this.userDataSource = userDataSource;
        this.recipeCacheRepository = recipeCacheRepository;
    }

    public void execute(String recipeId) {
        Long userId = userDataSource.getAuthenticatedUser().getId();

        // Check MySQL cache first (Gemini/festival recipes), then MongoDB
        boolean exists = recipeCacheRepository.findByRecipeId(recipeId).isPresent();
        if (!exists) {
            try {
                exists = mongoTemplate.findById(recipeId, RecipeDocument.class) != null;
            } catch (Exception ignored) {}
        }
        if (!exists) {
            throw new IllegalArgumentException("Recipe not found: " + recipeId);
        }

        if (bookmarkRepository.existsByUserIdAndRecipeId(userId, recipeId)) {
            return; // already bookmarked — idempotent
        }

        bookmarkRepository.save(BookmarkEntity.builder()
                .userId(userId)
                .recipeId(recipeId)
                .build());
    }
}
