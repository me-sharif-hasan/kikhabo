package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.infrastructure.config.MongoConfig;
import com.iishanto.kikhabo.web.response.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/v1/admin/recipes")
@Tag(name = "Recipe Admin", description = "Admin operations for recipe data")
public class RecipeAdminController {

    private final MongoConfig mongoConfig;

    @Value("${kikhabo.admin.api-key}")
    private String adminApiKey;

    public RecipeAdminController(MongoConfig mongoConfig) {
        this.mongoConfig = mongoConfig;
    }

    @PostMapping("/retag-exclusions")
    public ResponseEntity<SuccessResponse<Void>> retagExclusions(
            @RequestHeader("X-Api-Key") String apiKey) {

        if (!adminApiKey.equals(apiKey)) {
            return ResponseEntity.status(401)
                    .body(new SuccessResponse<>("error", "Unauthorized", null));
        }

        mongoConfig.tagExcludedRecipes();
        return ResponseEntity.ok(new SuccessResponse<>("Exclusion tagging complete"));
    }
}
