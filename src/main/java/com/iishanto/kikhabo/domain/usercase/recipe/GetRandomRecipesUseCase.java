package com.iishanto.kikhabo.domain.usercase.recipe;

import com.iishanto.kikhabo.domain.datasource.UserDataSource;
import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import com.iishanto.kikhabo.infrastructure.services.recipe.CountryService;
import com.iishanto.kikhabo.infrastructure.services.recipe.RecommendationService;
import com.iishanto.kikhabo.web.dto.recipe.RecipeDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetRandomRecipesUseCase {

    private static final int MAX_LIMIT = 50;

    private final MongoTemplate mongoTemplate;
    private final UserDataSource userDataSource;
    private final CountryService countryService;
    private final RecommendationService recommendationService;

    public GetRandomRecipesUseCase(MongoTemplate mongoTemplate,
                                   UserDataSource userDataSource,
                                   CountryService countryService,
                                   RecommendationService recommendationService) {
        this.mongoTemplate = mongoTemplate;
        this.userDataSource = userDataSource;
        this.countryService = countryService;
        this.recommendationService = recommendationService;
    }

    /**
     * Returns random recipes filtered by country/continent.
     *
     * Priority for country resolution:
     *   1. explicit `country` param  (matches recipe.country field — requires enriched data)
     *   2. explicit `continent` + `subcontinent` params
     *   3. authenticated user's country → resolved to continent
     *   4. no filter — random from entire collection
     */
    public List<RecipeDto> execute(String country, String continent, String subcontinent, int limit) {
        limit = Math.min(limit, MAX_LIMIT);

        Criteria baseCriteria = new Criteria().andOperator(
                Criteria.where("excluded").ne(true),
                Criteria.where("image").exists(true).nin(null, "")
        );

        Criteria filterCriteria = resolveFilter(country, continent, subcontinent);

        Criteria finalCriteria = filterCriteria != null
                ? new Criteria().andOperator(baseCriteria, filterCriteria)
                : baseCriteria;

        MatchOperation match = Aggregation.match(finalCriteria);
        SampleOperation sample = Aggregation.sample(limit);

        AggregationResults<RecipeDocument> results = mongoTemplate.aggregate(
                Aggregation.newAggregation(match, sample),
                RecipeDocument.class,
                RecipeDocument.class
        );

        return results.getMappedResults().stream().map(RecipeDto::from).toList();
    }

    private Criteria resolveFilter(String country, String continent, String subcontinent) {
        // 1. explicit country → match recipe.country field directly
        if (country != null && !country.isBlank()) {
            return Criteria.where("country").is(country.trim());
        }

        // 2. explicit continent param
        if (continent != null && !continent.isBlank()) {
            return buildContinentCriteria(continent, subcontinent);
        }

        // 3. infer from authenticated user's country
        try {
            String userCountry = userDataSource.getAuthenticatedUser().getCountry();
            if (userCountry != null && !userCountry.isBlank()) {
                // try exact country field first
                long countByCountry = mongoTemplate.count(
                        org.springframework.data.mongodb.core.query.Query.query(
                                Criteria.where("country").is(userCountry)
                        ),
                        RecipeDocument.class
                );
                if (countByCountry > 0) {
                    return Criteria.where("country").is(userCountry);
                }
                // fall back to continent recommendation terms
                CountryService.Region region = countryService.resolve(userCountry);
                if (region != null) {
                    return buildContinentCriteria(region.getContinent(), region.getSubcontinent());
                }
            }
        } catch (Exception ignored) {
            // unauthenticated or no country set — fall through to no filter
        }

        return null; // no filter — fully random
    }

    private Criteria buildContinentCriteria(String continent, String subcontinent) {
        List<String> terms = recommendationService.getTerms(continent, subcontinent);
        if (terms.isEmpty()) return null;

        String pattern = String.join("|", terms);
        return new Criteria().orOperator(
                Criteria.where("name").regex(pattern, "i"),
                Criteria.where("ingredients").regex(pattern, "i")
        );
    }
}
