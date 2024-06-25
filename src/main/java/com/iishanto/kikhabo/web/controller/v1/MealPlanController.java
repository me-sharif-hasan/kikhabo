package com.iishanto.kikhabo.web.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/meal-planning")
public class MealPlanController {
    @PostMapping
    public String generateMealSuggestion(){
        return null;
    }
}
