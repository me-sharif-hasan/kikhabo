package com.iishanto.kikhabo.domain.usercase.recipe;

import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.infrastructure.model.RecipeCacheEntity;
import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import com.iishanto.kikhabo.infrastructure.repositories.database.BookmarkRepository;
import com.iishanto.kikhabo.infrastructure.repositories.database.RecipeCacheRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GetBookmarkedRecipesUseCase {

    private final BookmarkRepository bookmarkRepository;
    private final MongoTemplate mongoTemplate;
    private final UserDataSource userDataSource;
    private final RecipeCacheRepository recipeCacheRepository;

    public GetBookmarkedRecipesUseCase(BookmarkRepository bookmarkRepository,
                                       MongoTemplate mongoTemplate,
                                       UserDataSource userDataSource,
                                       RecipeCacheRepository recipeCacheRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.mongoTemplate = mongoTemplate;
        this.userDataSource = userDataSource;
        this.recipeCacheRepository = recipeCacheRepository;
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

        // Search MongoDB recipes
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
        long mongoTotal = mongoTemplate.count(countQuery, RecipeDocument.class);

        List<RecipeDto> dtos = new ArrayList<>(docs.stream().map(RecipeDto::from).toList());

        // Also search MySQL-cached recipes (Gemini/festival) within bookmarked IDs
        Set<String> mongoIds = docs.stream().map(RecipeDocument::getId).collect(Collectors.toSet());
        String lowerSearch = search.toLowerCase();
        List<RecipeDto> cachedDtos = recipeCacheRepository.findByRecipeIdIn(
                        allIds.stream()
                                .filter(id -> !mongoIds.contains(id))
                                .collect(Collectors.toList()))
                .stream()
                .filter(e -> !e.isExcluded())
                .filter(e -> matches(e, lowerSearch))
                .map(RecipeDto::fromCache)
                .toList();

        dtos.addAll(cachedDtos);
        long total = mongoTotal + cachedDtos.size();

        return new RecipePageResponse(dtos, total, (int) Math.ceil((double) total / size), page, size, "bookmark-search");
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private List<RecipeDto> fetchByIds(List<String> ids) {
        if (ids.isEmpty()) return List.of();

        // Fetch from MongoDB
        List<RecipeDocument> docs;
        try {
            docs = mongoTemplate.find(Query.query(Criteria.where("_id").in(ids)), RecipeDocument.class);
        } catch (Exception e) {
            docs = List.of();
        }
        Map<String, RecipeDto> resolved = docs.stream()
                .collect(Collectors.toMap(RecipeDocument::getId, RecipeDto::from));

        // Fetch remaining IDs from MySQL cache (Gemini/festival recipes)
        List<String> missing = ids.stream().filter(id -> !resolved.containsKey(id)).toList();
        if (!missing.isEmpty()) {
            recipeCacheRepository.findByRecipeIdIn(missing).forEach(e -> resolved.put(e.getRecipeId(), RecipeDto.fromCache(e)));
        }

        // Preserve bookmark order (newest-first from MySQL)
        return ids.stream()
                .filter(resolved::containsKey)
                .map(resolved::get)
                .toList();
    }

    private boolean matches(RecipeCacheEntity e, String lowerSearch) {
        return (e.getName() != null && e.getName().toLowerCase().contains(lowerSearch))
                || (e.getNameNormalized() != null && e.getNameNormalized().contains(lowerSearch))
                || (e.getIngredients() != null && e.getIngredients().toLowerCase().contains(lowerSearch))
                || (e.getDescription() != null && e.getDescription().toLowerCase().contains(lowerSearch));
    }
}
