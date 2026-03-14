package com.iishanto.kikhabo.domain.usercase.recipe;

import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
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
    public RecipeSearchUseCase(MongoTemplate mongoTemplate,
                               RecommendationService recommendationService,
                               CountryService countryService,
                               UserDataSource userDataSource) {
        this.mongoTemplate = mongoTemplate;
        this.recommendationService = recommendationService;
        this.countryService = countryService;
        this.userDataSource = userDataSource;
    }

    /**
     * @param search       free-text query — when provided, recommendation terms are ignored
     * @param continent    explicit continent filter (recommendation mode only)
     * @param subcontinent explicit subcontinent filter (recommendation mode only)
     * @param page         0-based page number
     * @param size         page size (max 50)
     *
     * Priority when no search query:
     *   1. explicit continent param
     *   2. continent inferred from authenticated user's country
     *   3. broad fallback
     */
    public RecipePageResponse execute(String search, String continent, String subcontinent, int page, int size) {
        size = Math.min(size, 50);

        boolean isSearch = search != null && !search.isBlank();

        TextCriteria criteria;
        if (isSearch) {
            criteria = buildSearchCriteria(search.trim());
        } else {
            String resolvedContinent    = continent;
            String resolvedSubcontinent = subcontinent;

            // no explicit continent — try to infer from user's country
            if (resolvedContinent == null || resolvedContinent.isBlank()) {
                CountryService.Region region = resolveUserRegion();
                if (region != null) {
                    resolvedContinent    = region.getContinent();
                    resolvedSubcontinent = region.getSubcontinent();
                }
            }

            criteria = buildRecommendationCriteria(resolvedContinent, resolvedSubcontinent);
        }

        Criteria exclusion = buildExclusionCriteria();
        Criteria hasImage = Criteria.where("image").exists(true).nin(null, "");

        Query query = TextQuery.queryText(criteria).sortByScore();
        query.addCriteria(exclusion);
        query.addCriteria(hasImage);
        query.with(PageRequest.of(page, size));

        Query countQuery = TextQuery.queryText(criteria);
        countQuery.addCriteria(exclusion);
        countQuery.addCriteria(hasImage);

        List<RecipeDocument> docs = mongoTemplate.find(query, RecipeDocument.class);
        long total = mongoTemplate.count(countQuery, RecipeDocument.class);

        List<RecipeDto> dtos = docs.stream().map(RecipeDto::from).toList();
        int totalPages = (int) Math.ceil((double) total / size);

        return new RecipePageResponse(dtos, total, totalPages, page, size,
                isSearch ? "search" : "recommendation");
    }

    // ── private helpers ───────────────────────────────────────────────────────

    private TextCriteria buildSearchCriteria(String query) {
        return TextCriteria.forDefaultLanguage().matching(query);
    }

    private TextCriteria buildRecommendationCriteria(String continent, String subcontinent) {
        List<String> terms = recommendationService.getTerms(continent, subcontinent);
        if (terms.isEmpty()) {
            return TextCriteria.forDefaultLanguage().matching("recipe");
        }
        return TextCriteria.forDefaultLanguage().matchingAny(terms.toArray(new String[0]));
    }

    /**
     * Excludes recipes tagged at startup as containing prohibited food.
     * Index-backed field lookup — no regex cost per query.
     */
    private Criteria buildExclusionCriteria() {
        return Criteria.where("excluded").ne(true);
    }

    /** Resolves the authenticated user's country to a Region. Returns null if unauthenticated or country not set. */
    private CountryService.Region resolveUserRegion() {
        try {
            String country = userDataSource.getAuthenticatedUser().getCountry();
            return countryService.resolve(country);
        } catch (Exception e) {
            return null;  // unauthenticated request or no country on profile
        }
    }
}
