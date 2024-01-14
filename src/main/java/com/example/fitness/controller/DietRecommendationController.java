package com.example.fitness.controller;

import com.example.fitness.model.DietRecommedation;
import com.example.fitness.model.Food;
import com.example.fitness.service.DietRecommendationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dietRecommendation")
@CrossOrigin(value = "*",maxAge = 0)
public class DietRecommendationController {
    private final DietRecommendationService dietRecommendationService;

    public DietRecommendationController(DietRecommendationService dietRecommendationService) {
        this.dietRecommendationService = dietRecommendationService;
    }

    @PostMapping("/{guestId}/{trainerUserId}")
    public DietRecommedation saveDietRecommendation(@RequestBody DietRecommedation dietRecommedation, @PathVariable Integer guestId, @PathVariable Integer trainerUserId){
        return dietRecommendationService.save(dietRecommedation, guestId, trainerUserId);
    }
}
