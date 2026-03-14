package com.iishanto.kikhabo.domain.usercase.recipe;

import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.infrastructure.model.BookmarkEntity;
import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import com.iishanto.kikhabo.infrastructure.repositories.database.BookmarkRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookmarkRecipeUseCase {

    private final BookmarkRepository bookmarkRepository;
    private final MongoTemplate mongoTemplate;
    private final UserDataSource userDataSource;

    public BookmarkRecipeUseCase(BookmarkRepository bookmarkRepository,
                                 MongoTemplate mongoTemplate,
                                 UserDataSource userDataSource) {
        this.bookmarkRepository = bookmarkRepository;
        this.mongoTemplate = mongoTemplate;
        this.userDataSource = userDataSource;
    }

    public void execute(String recipeId) {
        Long userId = userDataSource.getAuthenticatedUser().getId();

        if (mongoTemplate.findById(recipeId, RecipeDocument.class) == null) {
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
