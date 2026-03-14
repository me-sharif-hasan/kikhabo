package com.iishanto.kikhabo.web.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecipePageResponse {
    private List<RecipeDto> recipes;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private String mode; // "search" or "recommendation"
}
