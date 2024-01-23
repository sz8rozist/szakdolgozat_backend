package com.example.fitness.controller;

import com.example.fitness.model.Diet;
import com.example.fitness.model.dto.CaloriesSumDto;
import com.example.fitness.model.dto.DietSummaryDto;
import com.example.fitness.model.dto.MealFrequencyDto;
import com.example.fitness.model.request.DietRequest;
import com.example.fitness.model.request.DietUpdateRequest;
import com.example.fitness.model.dto.DietGuestDto;
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
    public DietGuestDto getDietByDateAndUserId(@PathVariable Integer guestId, @PathVariable LocalDate dietDate){
        return dietService.getDietByDateAndUserId(guestId, dietDate);
    }

    @DeleteMapping("/{guestId}/{dietDate}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiet(@PathVariable Integer guestId, @PathVariable LocalDate dietDate){
        dietService.deleteDiet(guestId, dietDate);
    }

    @DeleteMapping("/food/{dietId}/{guestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer dietId,@PathVariable Integer guestId){
        dietService.deleteFood(dietId, guestId);
    }

    @GetMapping("/{dietId}")
    public Diet getDietById(@PathVariable Integer dietId){
        return dietService.getDietById(dietId);
    }

    @PutMapping("/{dietId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDiet(@RequestBody DietUpdateRequest dietUpdateRequest, @PathVariable Integer dietId){
        dietService.updateDiet(dietUpdateRequest, dietId);
    }

    @GetMapping("/macronutriense/{guestUserId}")
    public List<DietSummaryDto> getMacronutrienseStatistics(@PathVariable Integer guestUserId){
        return dietService.getMacronutrienseStatisztics(guestUserId);
    }

    @GetMapping("/caloriesSum/{userId}")
    public CaloriesSumDto getCalories(@PathVariable Integer userId){
        return dietService.getCalores(userId);
    }

    @GetMapping("/mealFrequency/{userId}")
    public List<MealFrequencyDto> getMealFrequency(@PathVariable Integer userId){
        return dietService.getMealFrequency(userId);
    }
 }
