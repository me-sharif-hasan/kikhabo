package com.iishanto.kikhabo.domain.usercase.recipe;

import com.iishanto.kikhabo.infrastructure.model.RecipeCacheEntity;
import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import com.iishanto.kikhabo.infrastructure.repositories.database.RecipeCacheRepository;
import com.iishanto.kikhabo.infrastructure.services.recipe.RecipeGuideService;
import com.iishanto.kikhabo.web.dto.recipe.RecipeDetailDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class GetRecipeDetailUseCase {

    private final MongoTemplate mongoTemplate;
    private final RecipeGuideService recipeGuideService;
    private final RecipeCacheRepository recipeCacheRepository;

    public GetRecipeDetailUseCase(MongoTemplate mongoTemplate,
                                  RecipeGuideService recipeGuideService,
                                  RecipeCacheRepository recipeCacheRepository) {
        this.mongoTemplate = mongoTemplate;
        this.recipeGuideService = recipeGuideService;
        this.recipeCacheRepository = recipeCacheRepository;
    }

    public RecipeDetailDto execute(String id) {
        // 1. MySQL first — cache hit
        RecipeCacheEntity cached = recipeCacheRepository.findByRecipeId(id).orElse(null);
        if (cached != null) {
            return RecipeDetailDto.fromCache(cached);
        }

        // 2. MongoDB fallback — works for both 24-char ObjectIds and UUIDs
        // UUIDs will simply return null (not a valid ObjectId, no document exists)
        RecipeDocument recipe;
        try {
            recipe = mongoTemplate.findById(id, RecipeDocument.class);
        } catch (Exception e) {
            return null;
        }
        if (recipe == null) return null;

        // 3. Generate cooking guide (Gemini, cached back in Mongo by RecipeGuideService)
        String guide = recipeGuideService.getOrGenerate(recipe);

        // 4. Write-through to MySQL so next request never hits MongoDB again
        try {
            RecipeCacheEntity entity = RecipeCacheEntity.fromDocument(recipe, guide);
            recipeCacheRepository.save(entity);
        } catch (Exception e) {
            // Non-fatal — cache write failure should not break the response
        }

        return RecipeDetailDto.from(recipe, guide);
    }
}
