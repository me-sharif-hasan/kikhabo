package com.iishanto.kikhabo.web.controller.v1;

import com.iishanto.kikhabo.domain.entities.meal.MealHistory;
import com.iishanto.kikhabo.domain.usercase.meal.GetMealDetailsBatchUseCase;
import com.iishanto.kikhabo.domain.usercase.meal.GetMealHistoryUseCase;
import com.iishanto.kikhabo.domain.usercase.meal.MealHistoryUpdateUserCase;
import com.iishanto.kikhabo.web.dto.meal.MealDetailDto;
import com.iishanto.kikhabo.web.dto.meal.MealHistoryDto;
import com.iishanto.kikhabo.web.dto.meal.MealRatingStatusDto;
import com.iishanto.kikhabo.web.dto.meal.PagedMealHistoryResponse;
import com.iishanto.kikhabo.web.response.SuccessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/meal-history")
@AllArgsConstructor
@Tag(name = "Meal History", description = "Manage meal history")
public class MealHistoryController {
        MealHistoryUpdateUserCase mealHistoryUpdateUserCase;
        GetMealHistoryUseCase getMealHistoryUseCase;
        GetMealDetailsBatchUseCase getMealDetailsBatchUseCase;

        @GetMapping
        public ResponseEntity<SuccessResponse<PagedMealHistoryResponse>> getMealHistory(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {

                Page<MealHistory> mealHistoryPage = getMealHistoryUseCase.execute(page, size);

                List<MealHistoryDto> content = mealHistoryPage.getContent().stream()
                                .map(MealHistoryDto::fromDomain)
                                .collect(Collectors.toList());

                PagedMealHistoryResponse response = PagedMealHistoryResponse.builder()
                                .content(content)
                                .page(mealHistoryPage.getNumber())
                                .size(mealHistoryPage.getSize())
                                .totalElements(mealHistoryPage.getTotalElements())
                                .totalPages(mealHistoryPage.getTotalPages())
                                .last(mealHistoryPage.isLast())
                                .build();

                return new ResponseEntity<>(new SuccessResponse<>("success", "Meal history retrieved", response),
                                HttpStatus.OK);
        }

        @PostMapping("details")
        public ResponseEntity<SuccessResponse<List<MealDetailDto>>> getMealDetails(
                        @RequestBody List<Long> mealHistoryIds) {
                List<MealDetailDto> result = getMealDetailsBatchUseCase.execute(mealHistoryIds)
                                .stream()
                                .map(MealDetailDto::fromDomain)
                                .collect(Collectors.toList());
                return new ResponseEntity<>(
                                new SuccessResponse<>("success", "Meal details retrieved", result),
                                HttpStatus.OK);
        }

        @PutMapping("update")
        public ResponseEntity<SuccessResponse<Boolean>> updateMealHistory(
                        @RequestBody List<MealRatingStatusDto> mealRatingStatusDtos) {
                List<MealHistory> mealHistories = mealRatingStatusDtos.stream()
                                .map(dto -> MealHistory.builder()
                                                .id(dto.getId())
                                                .mealStatus(dto.getMealStatus())
                                                .rating(dto.getRating())
                                                .userNote(dto.getUserNote())
                                                .build())
                                .toList();

                Boolean result = mealHistoryUpdateUserCase.execute(mealHistories);
                return new ResponseEntity<>(
                                new SuccessResponse<>("success", "Meal history updated successfully", result),
                                HttpStatus.OK);
        }
}
