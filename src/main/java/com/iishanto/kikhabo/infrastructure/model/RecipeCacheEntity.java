package com.iishanto.kikhabo.infrastructure.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "recipe_cache", uniqueConstraints = {
        @UniqueConstraint(name = "uq_mongo_country", columnNames = {"mongo_id", "country"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeCacheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Stable public-facing ID used in API responses and notification extras.
     * For MongoDB-mirrored recipes: equals mongo_id (24-char hex).
     * For Gemini-generated recipes: a UUID.
     */
    @Column(name = "recipe_id", nullable = false, unique = true, length = 36)
    private String recipeId;

    /**
     * Original MongoDB _id. Null for Gemini-generated recipes.
     */
    @Column(name = "mongo_id", length = 24)
    private String mongoId;

    @Column(nullable = false, length = 500)
    private String name;

    /**
     * Lowercase trimmed name — dedup key for name-based lookups (e.g. from scheduler).
     */
    @Column(name = "name_normalized", length = 500)
    private String nameNormalized;

    /**
     * Original recipe name as stored in MongoDB (English) — used for Pexels image search.
     */
    @Column(name = "mongo_name", length = 500)
    private String mongoName;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Column(columnDefinition = "TEXT")
    private String url;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column
    private String source;

    @Column(name = "recipe_yield", length = 100)
    private String recipeYield;

    @Column(name = "date_published", length = 100)
    private String datePublished;

    @Column(name = "cook_time", length = 100)
    private String cookTime;

    @Column(name = "prep_time", length = 100)
    private String prepTime;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String country;

    @Column(name = "cooking_guide", columnDefinition = "LONGTEXT")
    private String cookingGuide;

    @Column(name = "recipe_reason", length = 100)
    private String recipeReason;

    @Column(nullable = false)
    private boolean excluded = false;

    @Column(name = "cached_at")
    private LocalDateTime cachedAt;

    @PrePersist
    void prePersist() {
        if (cachedAt == null) cachedAt = LocalDateTime.now();
        if (nameNormalized == null && name != null) nameNormalized = name.toLowerCase().trim();
    }

    public static RecipeCacheEntity fromDocument(RecipeDocument doc, String cookingGuide) {
        return RecipeCacheEntity.builder()
                .recipeId(doc.getId())
                .mongoId(doc.getId())
                .name(doc.getName())
                .nameNormalized(doc.getName() != null ? doc.getName().toLowerCase().trim() : null)
                .ingredients(doc.getIngredients())
                .url(doc.getUrl())
                .image(doc.getImage())
                .source(doc.getSource())
                .recipeYield(doc.getRecipeYield())
                .datePublished(doc.getDatePublished())
                .cookTime(doc.getCookTime())
                .prepTime(doc.getPrepTime())
                .description(doc.getDescription())
                .country(doc.getCountry())
                .cookingGuide(cookingGuide)
                .excluded(doc.isExcluded())
                .build();
    }
}
