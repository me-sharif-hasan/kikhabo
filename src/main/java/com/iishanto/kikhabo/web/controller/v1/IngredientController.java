package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.domain.entities.ingredient.DetectedIngredient;
import com.iishanto.kikhabo.domain.usercase.ingredient.DetectIngredientsCommand;
import com.iishanto.kikhabo.domain.usercase.ingredient.DetectIngredientsUseCase;
import com.iishanto.kikhabo.web.dto.ingredient.DetectedIngredientDto;
import com.iishanto.kikhabo.web.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/ingredients")
@Tag(name = "Ingredient Detection", description = "Upload a food/kitchen image to detect cooking ingredients using Gemini Vision AI.")
public class IngredientController {

    private final DetectIngredientsUseCase detectIngredientsUseCase;

    @PostMapping(value = "/detect", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Detect cooking ingredients from an image",
            description = "Upload a food or kitchen image (JPEG/PNG, up to 10 MB). " +
                    "Returns detected cooking ingredients with estimated quantities. " +
                    "Only ingredients with confidence ≥ 70% are included."
    )
    public ResponseEntity<SuccessResponse<List<DetectedIngredientDto>>> detectIngredients(
            @RequestPart("image") MultipartFile image) throws Exception {

        DetectIngredientsCommand command = new DetectIngredientsCommand(
                image.getBytes(),
                image.getContentType()
        );

        List<DetectedIngredient> ingredients = detectIngredientsUseCase.execute(command);
        List<DetectedIngredientDto> dtos = ingredients.stream()
                .map(DetectedIngredientDto::fromDomain)
                .toList();

        return new ResponseEntity<>(
                new SuccessResponse<>("success", "Ingredients detected successfully", dtos),
                HttpStatus.OK
        );
    }
}
