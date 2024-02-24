package com.example.fitness.controller;

import com.example.fitness.model.Diet;
import com.example.fitness.model.dto.*;
import com.example.fitness.model.request.DietRequest;
import com.example.fitness.model.request.DietUpdateRequest;
import com.example.fitness.service.DietService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('GUEST', 'TRAINER')")
    public DietGuestDto getDietByDateAndUserId(@PathVariable Integer guestId, @PathVariable LocalDate dietDate){
        return dietService.getDietByDateAndUserId(guestId, dietDate);
    }

    @DeleteMapping("/{guestId}/{dietDate}")
    @PreAuthorize("hasAnyAuthority('GUEST', 'TRAINER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiet(@PathVariable Integer guestId, @PathVariable LocalDate dietDate){
        dietService.deleteDiet(guestId, dietDate);
    }

    @DeleteMapping("/food/{dietId}/{guestId}")
    @PreAuthorize("hasAnyAuthority('GUEST', 'TRAINER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer dietId,@PathVariable Integer guestId){
        dietService.deleteFood(dietId, guestId);
    }

    @GetMapping("/{dietId}")
    @PreAuthorize("hasAnyAuthority('GUEST', 'TRAINER')")
    public Diet getDietById(@PathVariable Integer dietId){
        return dietService.getDietById(dietId);
    }

    @PutMapping("/{dietId}")
    @PreAuthorize("hasAnyAuthority('GUEST', 'TRAINER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDiet(@RequestBody DietUpdateRequest dietUpdateRequest, @PathVariable Integer dietId){
        dietService.updateDiet(dietUpdateRequest, dietId);
    }

    @GetMapping("/macronutriense/{guestUserId}")
    @PreAuthorize("hasAnyAuthority('GUEST', 'TRAINER')")
    public List<DietSummaryDto> getMacronutrienseStatistics(@PathVariable Integer guestUserId){
        return dietService.getMacronutrienseStatisztics(guestUserId);
    }

    @GetMapping("/caloriesSum/{userId}")
    @PreAuthorize("hasAnyAuthority('GUEST', 'TRAINER')")
    public CaloriesSumDto getCalories(@PathVariable int userId){
        return dietService.getCalores(userId);
    }

    @GetMapping("/mealFrequency/{userId}")
    @PreAuthorize("hasAnyAuthority('GUEST', 'TRAINER')")
    public List<MealFrequencyDto> getMealFrequency(@PathVariable int userId){
        return dietService.getMealFrequency(userId);
    }

    @GetMapping("/getAllDietByGuestId/{guestId}")
    @PreAuthorize("hasAnyAuthority('GUEST', 'TRAINER')")
    public List<CalendarEventDto> getALlDietByGuest(@PathVariable int guestId){
        return dietService.getAllDietByGuestId(guestId);
    }
    @GetMapping("/getAllTrainerGuestDiet/{trainerId}")
    @PreAuthorize("hasAnyAuthority('GUEST', 'TRAINER')")
    public List<CalendarEventDto> getAllTrainerGuestDiet(@PathVariable int trainerId){
        return dietService.getAllTrainerGuestDiet(trainerId);
    }

    @GetMapping("/setEated/{dietId}")
    @PreAuthorize("hasAuthority('GUEST')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setEated(@PathVariable int dietId){
        dietService.setEated(dietId);
    }
 }
