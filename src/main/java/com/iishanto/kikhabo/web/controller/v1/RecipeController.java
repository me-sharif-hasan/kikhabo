package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.domain.usercase.recipe.BookmarkRecipeUseCase;
import com.iishanto.kikhabo.domain.usercase.recipe.GetBookmarkedRecipesUseCase;
import com.iishanto.kikhabo.domain.usercase.recipe.GetRandomRecipesUseCase;
import com.iishanto.kikhabo.domain.usercase.recipe.GetRecipeDetailUseCase;
import com.iishanto.kikhabo.domain.usercase.recipe.RemoveBookmarkUseCase;
import com.iishanto.kikhabo.domain.usercase.recipe.RecipeSearchUseCase;
import com.iishanto.kikhabo.web.dto.recipe.RecipeDetailDto;
import com.iishanto.kikhabo.web.dto.recipe.RecipeDto;
import com.iishanto.kikhabo.web.dto.recipe.RecipePageResponse;
import com.iishanto.kikhabo.web.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/recipes")
@Tag(name = "Recipes", description = "Browse and search static recipe catalogue")
public class RecipeController {

    private final RecipeSearchUseCase recipeSearchUseCase;
    private final GetRecipeDetailUseCase getRecipeDetailUseCase;
    private final GetRandomRecipesUseCase getRandomRecipesUseCase;
    private final BookmarkRecipeUseCase bookmarkRecipeUseCase;
    private final RemoveBookmarkUseCase removeBookmarkUseCase;
    private final GetBookmarkedRecipesUseCase getBookmarkedRecipesUseCase;

    public RecipeController(RecipeSearchUseCase recipeSearchUseCase,
                            GetRecipeDetailUseCase getRecipeDetailUseCase,
                            GetRandomRecipesUseCase getRandomRecipesUseCase,
                            BookmarkRecipeUseCase bookmarkRecipeUseCase,
                            RemoveBookmarkUseCase removeBookmarkUseCase,
                            GetBookmarkedRecipesUseCase getBookmarkedRecipesUseCase) {
        this.recipeSearchUseCase = recipeSearchUseCase;
        this.getRecipeDetailUseCase = getRecipeDetailUseCase;
        this.getRandomRecipesUseCase = getRandomRecipesUseCase;
        this.bookmarkRecipeUseCase = bookmarkRecipeUseCase;
        this.removeBookmarkUseCase = removeBookmarkUseCase;
        this.getBookmarkedRecipesUseCase = getBookmarkedRecipesUseCase;
    }

    @GetMapping
    @Operation(
        summary = "Browse or search recipes",
        description = """
            **Search mode** (`search` param provided):
            Performs full-text search on recipe name, ingredients and description.
            Recommendation terms are ignored.

            **Recommendation mode** (no `search` param):
            Uses cuisine tags from the given continent/subcontinent to suggest
            matching recipes via OR query. Highest-scoring matches appear first.
            """
    )
    public ResponseEntity<SuccessResponse<RecipePageResponse>> getRecipes(

            @Parameter(description = "Free-text search query. When provided, continent/subcontinent are ignored.")
            @RequestParam(required = false) String search,

            @Parameter(description = "Continent for recommendations (e.g. Asia, Europe, Americas, Africa, Oceania)")
            @RequestParam(required = false) String continent,

            @Parameter(description = "Subcontinent for narrower recommendations (e.g. Southern Asia, Eastern Asia)")
            @RequestParam(required = false) String subcontinent,

            @Parameter(description = "0-based page number")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Page size (max 50)")
            @RequestParam(defaultValue = "20") int size
    ) {
        RecipePageResponse result = recipeSearchUseCase.execute(search, continent, subcontinent, page, size);
        return ResponseEntity.ok(new SuccessResponse<>("success", "Recipes fetched", result));
    }

    @GetMapping("/bookmarks")
    @Operation(summary = "Get bookmarked recipes", description = "Returns the authenticated user's bookmarks, newest first. Supports optional search within bookmarks.")
    public ResponseEntity<SuccessResponse<RecipePageResponse>> getBookmarks(
            @Parameter(description = "Search within bookmarks only") @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(new SuccessResponse<>("success", "Bookmarks fetched",
                getBookmarkedRecipesUseCase.execute(search, page, size)));
    }

    @PostMapping("/{id}/bookmark")
    @Operation(summary = "Bookmark a recipe")
    public ResponseEntity<SuccessResponse<Void>> bookmark(@PathVariable String id) {
        bookmarkRecipeUseCase.execute(id);
        return ResponseEntity.ok(new SuccessResponse<>("Recipe bookmarked"));
    }

    @DeleteMapping("/{id}/bookmark")
    @Operation(summary = "Remove a recipe bookmark")
    public ResponseEntity<SuccessResponse<Void>> removeBookmark(@PathVariable String id) {
        removeBookmarkUseCase.execute(id);
        return ResponseEntity.ok(new SuccessResponse<>("Bookmark removed"));
    }

    @GetMapping("/random")
    @Operation(
        summary = "Random recipes by country/continent",
        description = """
            Returns randomized recipes. Every call returns a different set.

            **Country resolution priority:**
            1. `country` param — matches recipe's country field (e.g. Italy, Japan)
            2. `continent` + `subcontinent` params
            3. Authenticated user's saved country
            4. No filter — random from entire collection

            Max limit is 50.
            """
    )
    public ResponseEntity<SuccessResponse<List<RecipeDto>>> getRandomRecipes(
            @Parameter(description = "Filter by exact country (e.g. Italy, Japan, India)")
            @RequestParam(required = false) String country,

            @Parameter(description = "Filter by continent (e.g. Asia, Europe, Americas)")
            @RequestParam(required = false) String continent,

            @Parameter(description = "Filter by subcontinent (e.g. Southern Asia)")
            @RequestParam(required = false) String subcontinent,

            @Parameter(description = "Number of recipes to return (max 50)")
            @RequestParam(defaultValue = "20") int limit
    ) {
        List<RecipeDto> recipes = getRandomRecipesUseCase.execute(country, continent, subcontinent, limit);
        return ResponseEntity.ok(new SuccessResponse<>("success", "Random recipes fetched", recipes));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get recipe detail with cooking guide",
        description = "Returns full recipe including ingredients. " +
                      "Cooking guide is generated by Gemini on first request and cached permanently — subsequent calls return instantly."
    )
    public ResponseEntity<SuccessResponse<?>> getRecipeDetail(@PathVariable String id) {
        RecipeDetailDto detail = getRecipeDetailUseCase.execute(id);
        if (detail == null) {
            return ResponseEntity.status(404)
                    .body(new SuccessResponse<>("error", "Recipe not found", Map.of("id", id)));
        }
        return ResponseEntity.ok(new SuccessResponse<>("success", "Recipe detail fetched", detail));
    }
}
