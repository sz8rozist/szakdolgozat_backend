package com.example.fitness.controller;

import com.example.fitness.exception.DietRecommendationNotFoundException;
import com.example.fitness.model.DietRecommedation;
import com.example.fitness.model.Food;
import com.example.fitness.service.DietRecommendationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public DietRecommedation saveDietRecommendation(@RequestBody DietRecommedation dietRecommedation, @PathVariable int guestId, @PathVariable int trainerUserId){
        return dietRecommendationService.save(dietRecommedation, guestId, trainerUserId);
    }
    @GetMapping("/{guestUserId}/{date}")
    public DietRecommedation getRecommendationByDateAndGuest(@PathVariable int guestUserId, @PathVariable LocalDate date) throws DietRecommendationNotFoundException {
        return dietRecommendationService.getRecommendationByDateAndGuest(guestUserId, date);
    }
    @GetMapping("/guest/{guestId}/{trainerUserId}")
    public List<DietRecommedation> getAllRecommendation(@PathVariable int guestId, @PathVariable int trainerUserId){
        return dietRecommendationService.getAll(guestId, trainerUserId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        dietRecommendationService.delete(id);
    }

    @PutMapping("/{id}")
    public DietRecommedation update(@RequestBody DietRecommedation dietRecommedation, @PathVariable int id){
        return dietRecommendationService.update(id, dietRecommedation);
    }

    @GetMapping("/get/{id}")
    public DietRecommedation getById(@PathVariable int id){
        return  dietRecommendationService.getById(id);
    }
}
