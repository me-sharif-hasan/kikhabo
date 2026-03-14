package com.iishanto.kikhabo.infrastructure.config;

import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import com.iishanto.kikhabo.infrastructure.services.recipe.FoodExclusionConfig;
import com.mongodb.client.result.UpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@Configuration
@EnableMongoRepositories(basePackages = "com.iishanto.kikhabo.infrastructure.repositories.mongo")
public class MongoConfig implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MongoConfig.class);

    private final MongoTemplate mongoTemplate;
    private final FoodExclusionConfig foodExclusionConfig;

    public MongoConfig(MongoTemplate mongoTemplate, FoodExclusionConfig foodExclusionConfig) {
        this.mongoTemplate = mongoTemplate;
        this.foodExclusionConfig = foodExclusionConfig;
    }

    @Override
    public void run(ApplicationArguments args) {
        try { ensureTextIndex(); }    catch (Exception e) { log.error("[mongo] text index failed: {}", e.getMessage()); }
        try { ensureExcludedIndex(); } catch (Exception e) { log.error("[mongo] excluded index failed: {}", e.getMessage()); }
        try { tagExcludedRecipes(); }  catch (Exception e) { log.error("[mongo] tagging failed: {}", e.getMessage()); }
    }

    private void ensureTextIndex() {
        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("name", 10f)
                .onField("ingredients", 5f)
                .onField("description", 1f)
                .build();
        mongoTemplate.indexOps(RecipeDocument.class).ensureIndex(textIndex);
        log.info("[mongo] text index ensured");
    }

    private void ensureExcludedIndex() {
        mongoTemplate.indexOps(RecipeDocument.class)
                .ensureIndex(new Index().on("excluded", Sort.Direction.ASC).sparse());
        log.info("[mongo] excluded index ensured");
    }

    /**
     * Tags prohibited recipes with excluded=true.
     * Runs in small per-term batches to avoid Atlas M0 regex timeout
     * on a single huge compound pattern.
     * Safe to re-run — idempotent (setting true on an already-true doc is a no-op).
     */
    public void tagExcludedRecipes() {
        long total = 0;

        for (List<String> terms : foodExclusionConfig.getTermBatches()) {
            // Build a simple pipe-separated pattern per batch — no \b, case-insensitive
            String pattern = String.join("|", terms);

            Criteria match = new Criteria().orOperator(
                    Criteria.where("name").regex(pattern, "i"),
                    Criteria.where("ingredients").regex(pattern, "i")
            );

            UpdateResult result = mongoTemplate.updateMulti(
                    Query.query(match),
                    Update.update("excluded", true),
                    RecipeDocument.class
            );
            total += result.getModifiedCount();
        }

        log.info("[mongo] tagged {} recipes as excluded", total);
    }
}
