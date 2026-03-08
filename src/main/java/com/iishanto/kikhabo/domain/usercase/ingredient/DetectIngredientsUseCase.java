package com.iishanto.kikhabo.domain.usercase.ingredient;

import com.iishanto.kikhabo.domain.datasource.IngredientDetectionDataSource;
import com.iishanto.kikhabo.domain.entities.ingredient.DetectedIngredient;
import com.iishanto.kikhabo.domain.usercase.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DetectIngredientsUseCase implements UseCase<List<DetectedIngredient>, DetectIngredientsCommand> {

    private final IngredientDetectionDataSource ingredientDetectionDataSource;

    @Override
    public List<DetectedIngredient> execute(DetectIngredientsCommand command) throws Exception {
        return ingredientDetectionDataSource.detectIngredients(command.getImageBytes(), command.getMimeType());
    }
}
