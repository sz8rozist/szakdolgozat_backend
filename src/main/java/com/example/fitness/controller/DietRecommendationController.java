package com.example.fitness.controller;

import com.example.fitness.exception.DietRecommendationNotFoundException;
import com.example.fitness.model.DietRecommedation;
import com.example.fitness.model.Food;
import com.example.fitness.model.dto.NutritionDto;
import com.example.fitness.model.request.NutritionRequest;
import com.example.fitness.service.DietRecommendationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dietRecommendation")
@CrossOrigin(value = "*",maxAge = 0)
public class DietRecommendationController {
    private final DietRecommendationService dietRecommendationService;

    public DietRecommendationController(DietRecommendationService dietRecommendationService) {
        this.dietRecommendationService = dietRecommendationService;
    }

    @PostMapping("/{guestId}/{trainerUserId}")
    @PreAuthorize("hasAuthority('TRAINER')")
    public DietRecommedation saveDietRecommendation(@RequestBody DietRecommedation dietRecommedation, @PathVariable int guestId, @PathVariable int trainerUserId){
        return dietRecommendationService.save(dietRecommedation, guestId, trainerUserId);
    }
    @GetMapping("/{guestUserId}/{date}")
    @PreAuthorize("hasAuthority('TRAINER')")
    public DietRecommedation getRecommendationByDateAndGuest(@PathVariable int guestUserId, @PathVariable LocalDate date) throws DietRecommendationNotFoundException {
        return dietRecommendationService.getRecommendationByDateAndGuest(guestUserId, date);
    }
    @GetMapping("/guest/{guestId}/{trainerUserId}")
    @PreAuthorize("hasAuthority('TRAINER')")
    public List<DietRecommedation> getAllRecommendation(@PathVariable int guestId, @PathVariable int trainerUserId){
        return dietRecommendationService.getAll(guestId, trainerUserId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('TRAINER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        dietRecommendationService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('TRAINER')")
    public DietRecommedation update(@RequestBody DietRecommedation dietRecommedation, @PathVariable int id){
        return dietRecommendationService.update(id, dietRecommedation);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('TRAINER')")
    public DietRecommedation getById(@PathVariable int id){
        return  dietRecommendationService.getById(id);
    }

    @PostMapping("/nutritionCalculate")
    @PreAuthorize("hasAuthority('TRAINER')")
    public NutritionDto nutritionCalculate(@RequestBody NutritionRequest nutritionRequest){
        return dietRecommendationService.nutiritonCalculate(nutritionRequest);
    }
}
