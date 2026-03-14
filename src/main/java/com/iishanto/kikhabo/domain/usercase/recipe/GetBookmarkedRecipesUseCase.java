package com.iishanto.kikhabo.domain.usercase.recipe;

import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import com.iishanto.kikhabo.infrastructure.repositories.database.BookmarkRepository;
import com.iishanto.kikhabo.web.dto.recipe.RecipeDto;
import com.iishanto.kikhabo.web.dto.recipe.RecipePageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetBookmarkedRecipesUseCase {

    private final BookmarkRepository bookmarkRepository;
    private final MongoTemplate mongoTemplate;
    private final UserDataSource userDataSource;

    public GetBookmarkedRecipesUseCase(BookmarkRepository bookmarkRepository,
                                       MongoTemplate mongoTemplate,
                                       UserDataSource userDataSource) {
        this.bookmarkRepository = bookmarkRepository;
        this.mongoTemplate = mongoTemplate;
        this.userDataSource = userDataSource;
    }

    public RecipePageResponse execute(String search, int page, int size) {
        size = Math.min(size, 50);
        Long userId = userDataSource.getAuthenticatedUser().getId();

        boolean isSearch = search != null && !search.isBlank();

        if (isSearch) {
            return searchWithinBookmarks(userId, search.trim(), page, size);
        } else {
            return pagedBookmarks(userId, page, size);
        }
    }

    // ── No search: fetch by bookmarked IDs, preserve bookmark order ───────────

    private RecipePageResponse pagedBookmarks(Long userId, int page, int size) {
        Page<String> idPage = bookmarkRepository.findRecipeIdsByUserIdPaged(
                userId, PageRequest.of(page, size));

        List<RecipeDto> recipes = fetchByIds(idPage.getContent());

        return new RecipePageResponse(
                recipes,
                idPage.getTotalElements(),
                idPage.getTotalPages(),
                page, size,
                "bookmark"
        );
    }

    // ── With search: text search scoped to user's bookmarked IDs ─────────────

    private RecipePageResponse searchWithinBookmarks(Long userId, String search, int page, int size) {
        List<String> allIds = bookmarkRepository.findRecipeIdsByUserId(userId);

        if (allIds.isEmpty()) {
            return new RecipePageResponse(List.of(), 0, 0, page, size, "bookmark-search");
        }

        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(search);
        Criteria scope = new Criteria().andOperator(
                Criteria.where("_id").in(allIds),
                Criteria.where("excluded").ne(true)
        );

        Query query = TextQuery.queryText(textCriteria).sortByScore();
        query.addCriteria(scope);
        query.with(PageRequest.of(page, size));

        Query countQuery = TextQuery.queryText(textCriteria);
        countQuery.addCriteria(scope);

        List<RecipeDocument> docs = mongoTemplate.find(query, RecipeDocument.class);
        long total = mongoTemplate.count(countQuery, RecipeDocument.class);

        List<RecipeDto> dtos = docs.stream().map(RecipeDto::from).toList();
        return new RecipePageResponse(dtos, total, (int) Math.ceil((double) total / size), page, size, "bookmark-search");
    }

    private List<RecipeDto> fetchByIds(List<String> ids) {
        if (ids.isEmpty()) return List.of();
        List<RecipeDocument> docs = mongoTemplate.find(
                Query.query(Criteria.where("_id").in(ids)),
                RecipeDocument.class
        );
        // preserve bookmark order (MySQL returns them newest-first)
        return ids.stream()
                .flatMap(id -> docs.stream().filter(d -> d.getId().equals(id)))
                .map(RecipeDto::from)
                .toList();
    }
}
