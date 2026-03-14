package com.iishanto.kikhabo.domain.usercase.recipe;

import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import com.iishanto.kikhabo.infrastructure.services.recipe.RecipeGuideService;
import com.iishanto.kikhabo.web.dto.recipe.RecipeDetailDto;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class GetRecipeDetailUseCase {

    private final MongoTemplate mongoTemplate;
    private final RecipeGuideService recipeGuideService;

    public GetRecipeDetailUseCase(MongoTemplate mongoTemplate, RecipeGuideService recipeGuideService) {
        this.mongoTemplate = mongoTemplate;
        this.recipeGuideService = recipeGuideService;
    }

    public RecipeDetailDto execute(String id) {
        RecipeDocument recipe = mongoTemplate.findById(id, RecipeDocument.class);
        if (recipe == null) return null;

        String guide = recipeGuideService.getOrGenerate(recipe);
        return RecipeDetailDto.from(recipe, guide);
    }
}
