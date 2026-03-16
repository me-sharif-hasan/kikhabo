package com.iishanto.kikhabo.web.dto.recipe;

import com.iishanto.kikhabo.infrastructure.model.RecipeCacheEntity;
import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import lombok.Data;

@Data
public class RecipeDetailDto {

    private String id;
    private String name;
    private String ingredients;
    private String source;
    private String url;
    private String image;
    private String cookTime;
    private String prepTime;
    private String recipeYield;
    private String datePublished;
    private String description;
    private String country;
    private String cookingGuide;   // null if generation failed, populated otherwise

    public static RecipeDetailDto fromCache(RecipeCacheEntity cached) {
        RecipeDetailDto dto = new RecipeDetailDto();
        dto.id = cached.getRecipeId();
        dto.name = cached.getName();
        dto.ingredients = cached.getIngredients();
        dto.source = cached.getSource();
        dto.url = cached.getUrl();
        dto.image = cached.getImage();
        dto.cookTime = cached.getCookTime();
        dto.prepTime = cached.getPrepTime();
        dto.recipeYield = cached.getRecipeYield();
        dto.datePublished = cached.getDatePublished();
        dto.description = cached.getDescription();
        dto.country = cached.getCountry();
        dto.cookingGuide = cached.getCookingGuide();
        return dto;
    }

    public static RecipeDetailDto from(RecipeDocument doc, String cookingGuide) {
        RecipeDetailDto dto = new RecipeDetailDto();
        dto.id = doc.getId();
        dto.name = doc.getName();
        dto.ingredients = doc.getIngredients();
        dto.source = doc.getSource();
        dto.url = doc.getUrl();
        dto.image = doc.getImage();
        dto.cookTime = doc.getCookTime();
        dto.prepTime = doc.getPrepTime();
        dto.recipeYield = doc.getRecipeYield();
        dto.datePublished = doc.getDatePublished();
        dto.description = doc.getDescription();
        dto.country = doc.getCountry();
        dto.cookingGuide = cookingGuide;
        return dto;
    }
}
