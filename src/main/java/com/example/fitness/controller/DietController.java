package com.example.fitness.controller;

import com.example.fitness.model.Diet;
import com.example.fitness.model.request.DietRequest;
import com.example.fitness.service.DietService;
import org.springframework.web.bind.annotation.*;

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
}
