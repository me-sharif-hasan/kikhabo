package com.iishanto.kikhabo.web.dto.recipe;

import com.iishanto.kikhabo.infrastructure.model.RecipeCacheEntity;
import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import lombok.Data;

@Data
public class RecipeDto {

    private String id;
    private String name;
    private String source;
    private String url;
    private String image;
    private String cookTime;
    private String prepTime;
    private String recipeYield;
    private String datePublished;
    private String description;
    private String country;

    public static RecipeDto fromCache(RecipeCacheEntity cached) {
        RecipeDto dto = new RecipeDto();
        dto.id = cached.getRecipeId();
        dto.name = cached.getName();
        dto.source = cached.getSource();
        dto.url = cached.getUrl();
        dto.image = cached.getImage();
        dto.cookTime = cached.getCookTime();
        dto.prepTime = cached.getPrepTime();
        dto.recipeYield = cached.getRecipeYield();
        dto.datePublished = cached.getDatePublished();
        dto.description = cached.getDescription();
        dto.country = cached.getCountry();
        return dto;
    }

    public static RecipeDto from(RecipeDocument doc) {
        RecipeDto dto = new RecipeDto();
        dto.id = doc.getId();
        dto.name = doc.getName();
        dto.source = doc.getSource();
        dto.url = doc.getUrl();
        dto.image = doc.getImage();
        dto.cookTime = doc.getCookTime();
        dto.prepTime = doc.getPrepTime();
        dto.recipeYield = doc.getRecipeYield();
        dto.datePublished = doc.getDatePublished();
        dto.description = doc.getDescription();
        dto.country = doc.getCountry();
        return dto;
    }
}
