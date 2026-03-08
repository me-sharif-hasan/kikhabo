package com.iishanto.kikhabo.domain.datasource;

import com.iishanto.kikhabo.domain.entities.ingredient.DetectedIngredient;

import java.util.List;

public interface IngredientDetectionDataSource {
    List<DetectedIngredient> detectIngredients(byte[] imageBytes, String mimeType) throws Exception;
}
