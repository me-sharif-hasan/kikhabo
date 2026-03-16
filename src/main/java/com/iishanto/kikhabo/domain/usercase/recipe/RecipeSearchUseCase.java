package com.iishanto.kikhabo.domain.usercase.recipe;

import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.infrastructure.model.RecipeCacheEntity;
import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import com.iishanto.kikhabo.infrastructure.repositories.database.RecipeCacheRepository;
import com.iishanto.kikhabo.infrastructure.services.recipe.CountryService;
import com.iishanto.kikhabo.infrastructure.services.recipe.RecommendationService;
import com.iishanto.kikhabo.web.dto.recipe.RecipeDto;
import com.iishanto.kikhabo.web.dto.recipe.RecipePageResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeSearchUseCase {

    private final MongoTemplate mongoTemplate;
    private final RecommendationService recommendationService;
    private final CountryService countryService;
    private final UserDataSource userDataSource;
    private final RecipeCacheRepository recipeCacheRepository;

    public RecipeSearchUseCase(MongoTemplate mongoTemplate,
                               RecommendationService recommendationService,
                               CountryService countryService,
                               UserDataSource userDataSource,
                               RecipeCacheRepository recipeCacheRepository) {
        this.mongoTemplate = mongoTemplate;
        this.recommendationService = recommendationService;
        this.countryService = countryService;
        this.userDataSource = userDataSource;
        this.recipeCacheRepository = recipeCacheRepository;
    }

    /**
     * Recommendation mode (no search query):
     *   - Page comes from MySQL recipe_cache first, filtered by user's country.
     *   - Once MySQL pages are exhausted, falls back to MongoDB tag-based search.
     * Search mode (search query provided):
     *   - MongoDB full-text search only (tags/text index).
     */
    public RecipePageResponse execute(String search, String continent, String subcontinent, int page, int size) {
        size = Math.min(size, 50);

        boolean isSearch = search != null && !search.isBlank();

        if (!isSearch) {
            String userCountry = resolveUserCountry();
            if (userCountry != null) {
                return executeWithMysqlFirst(userCountry, continent, subcontinent, page, size);
            }
        }

        return executeMongoOnly(search, continent, subcontinent, page, size);
    }

    // ── MySQL-first recommendation (when user country is known) ───────────────

    private RecipePageResponse executeWithMysqlFirst(String country, String continent, String subcontinent, int page, int size) {
        long mysqlCount = recipeCacheRepository.countByCountryIgnoreCaseAndExcludedFalse(country);
        long mysqlTotalPages = (long) Math.ceil((double) mysqlCount / size);

        if (page < mysqlTotalPages) {
            // Serve from MySQL
            List<RecipeCacheEntity> cached = recipeCacheRepository
                    .findByCountryIgnoreCaseAndExcludedFalse(country, PageRequest.of(page, size));
            List<RecipeDto> dtos = cached.stream().map(RecipeDto::fromCache).toList();

            // Total is MySQL count + MongoDB count for accurate pagination
            long mongoTotal = countMongo(country, continent, subcontinent);
            long total = mysqlCount + mongoTotal;
            int totalPages = (int) Math.ceil((double) total / size);

            return new RecipePageResponse(dtos, total, totalPages, page, size, "recommendation");
        }

        // MySQL exhausted — fall back to MongoDB with adjusted page
        int mongoPage = (int) (page - mysqlTotalPages);
        TextCriteria criteria = buildRecommendationCriteria(
                resolveContinent(country, continent), subcontinent);
        return queryMongo(criteria, mongoPage, size, page, mysqlCount);
    }

    // ── MongoDB-only (search mode or no country) ──────────────────────────────

    private RecipePageResponse executeMongoOnly(String search, String continent, String subcontinent, int page, int size) {
        boolean isSearch = search != null && !search.isBlank();

        TextCriteria criteria = isSearch
                ? TextCriteria.forDefaultLanguage().matching(search.trim())
                : buildRecommendationCriteria(continent, subcontinent);

        return queryMongo(criteria, page, size, page, 0);
    }

    private RecipePageResponse queryMongo(TextCriteria criteria, int mongoPage, int size, int responsePage, long mysqlOffset) {
        Criteria exclusion = Criteria.where("excluded").ne(true);
        Criteria hasImage  = Criteria.where("image").exists(true).nin(null, "");

        Query query = TextQuery.queryText(criteria).sortByScore();
        query.addCriteria(exclusion);
        query.addCriteria(hasImage);
        query.with(PageRequest.of(mongoPage, size));

        Query countQuery = TextQuery.queryText(criteria);
        countQuery.addCriteria(exclusion);
        countQuery.addCriteria(hasImage);

        List<RecipeDocument> docs = mongoTemplate.find(query, RecipeDocument.class);
        long mongoTotal = mongoTemplate.count(countQuery, RecipeDocument.class);

        List<RecipeDto> dtos = docs.stream().map(RecipeDto::from).toList();
        long total = mysqlOffset + mongoTotal;
        int totalPages = (int) Math.ceil((double) total / size);

        return new RecipePageResponse(dtos, total, totalPages, responsePage, size, "recommendation");
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private long countMongo(String country, String continent, String subcontinent) {
        try {
            String resolvedContinent = resolveContinent(country, continent);
            TextCriteria criteria = buildRecommendationCriteria(resolvedContinent, subcontinent);
            Query countQuery = TextQuery.queryText(criteria);
            countQuery.addCriteria(Criteria.where("excluded").ne(true));
            countQuery.addCriteria(Criteria.where("image").exists(true).nin(null, ""));
            return mongoTemplate.count(countQuery, RecipeDocument.class);
        } catch (Exception e) {
            return 0;
        }
    }

    private String resolveContinent(String country, String explicitContinent) {
        if (explicitContinent != null && !explicitContinent.isBlank()) return explicitContinent;
        try {
            CountryService.Region region = countryService.resolve(country);
            return region != null ? region.getContinent() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private TextCriteria buildRecommendationCriteria(String continent, String subcontinent) {
        List<String> terms = recommendationService.getTerms(continent, subcontinent);
        if (terms.isEmpty()) {
            return TextCriteria.forDefaultLanguage().matching("recipe");
        }
        return TextCriteria.forDefaultLanguage().matchingAny(terms.toArray(new String[0]));
    }

    private String resolveUserCountry() {
        try {
            String country = userDataSource.getAuthenticatedUser().getCountry();
            return (country != null && !country.isBlank()) ? country : null;
        } catch (Exception e) {
            return null;
        }
    }
}
