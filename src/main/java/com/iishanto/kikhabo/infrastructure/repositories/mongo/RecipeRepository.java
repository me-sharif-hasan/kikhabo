package com.iishanto.kikhabo.infrastructure.repositories.mongo;

import com.iishanto.kikhabo.infrastructure.model.RecipeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<RecipeDocument, String> {
    // Custom text-search queries are handled via MongoTemplate in RecipeSearchService
}
