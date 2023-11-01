package com.example.fitness.controller;

import com.example.fitness.model.Diet;
import com.example.fitness.model.request.DietRequest;
import com.example.fitness.model.response.DietResponse;
import com.example.fitness.service.DietService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/diet")
@CrossOrigin(value = "*",maxAge = 0)
public class DietController {
    private final DietService dietService;

    public DietController(DietService dietService) {
        this.dietService = dietService;
    }
    @PostMapping
    public void saveDiet(@RequestBody List<DietRequest> dietFoodList){
        dietService.saveDiet(dietFoodList);
    }
    @GetMapping("/{guestId}/{dietDate}")
    public DietResponse getDietByDateAndUserId(@PathVariable Integer guestId, @PathVariable LocalDate dietDate){
        return dietService.getDietByDateAndUserId(guestId, dietDate);
    }

    @DeleteMapping("/{guestId}/{dietDate}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiet(@PathVariable Integer guestId, @PathVariable LocalDate dietDate){
        dietService.deleteDiet(guestId, dietDate);
    }

    @DeleteMapping("/food/{foodId}/{guestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer foodId,@PathVariable Integer guestId){
        dietService.deleteFood(foodId, guestId);
    }
 }
