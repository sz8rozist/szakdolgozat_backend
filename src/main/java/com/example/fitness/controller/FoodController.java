package com.example.fitness.controller;

import com.example.fitness.model.Food;
import com.example.fitness.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
@CrossOrigin(value = "*",maxAge = 0)
public class FoodController {
    @Autowired
    private FoodService foodService;
    @GetMapping("/{offset}/{pageSize}")
    public Page<Food> getAllFood(@PathVariable int offset, @PathVariable int pageSize){
        return foodService.getAllFood(offset, pageSize);
    }

    @PostMapping
    public Food saveFood(@Valid @RequestBody Food food){
        return foodService.saveFood(food);
    }

    @GetMapping
    public List<Food> getAllFoodsWithoutPagination(){
        return foodService.getAllFoodWithoutPagination();
    }
}
