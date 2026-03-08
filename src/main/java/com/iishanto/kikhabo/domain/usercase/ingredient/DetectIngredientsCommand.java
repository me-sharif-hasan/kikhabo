package com.iishanto.kikhabo.domain.usercase.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetectIngredientsCommand {
    private byte[] imageBytes;
    private String mimeType;
}
