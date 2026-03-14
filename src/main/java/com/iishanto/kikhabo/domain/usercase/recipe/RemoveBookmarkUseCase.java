package com.iishanto.kikhabo.domain.usercase.recipe;

import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.infrastructure.repositories.database.BookmarkRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RemoveBookmarkUseCase {

    private final BookmarkRepository bookmarkRepository;
    private final UserDataSource userDataSource;

    public RemoveBookmarkUseCase(BookmarkRepository bookmarkRepository, UserDataSource userDataSource) {
        this.bookmarkRepository = bookmarkRepository;
        this.userDataSource = userDataSource;
    }

    @Transactional
    public void execute(String recipeId) {
        Long userId = userDataSource.getAuthenticatedUser().getId();
        bookmarkRepository.deleteByUserIdAndRecipeId(userId, recipeId);
    }
}
