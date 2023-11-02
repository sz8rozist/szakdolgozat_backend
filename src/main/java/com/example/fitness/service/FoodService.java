package com.example.fitness.service;

import com.example.fitness.exception.FoodNotFoundException;
import com.example.fitness.model.DietGuest;
import com.example.fitness.model.Food;
import com.example.fitness.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    public Page<Food> getAllFood(int offSet, int pageSize) {
        return foodRepository.findAll(PageRequest.of(offSet,pageSize));
    }

    public Food saveFood(Food food) {
        return foodRepository.save(food);
    }

    public List<Food> getAllFoodWithoutPagination() {
        return foodRepository.findAll();
    }

    public Food getFoodById(Integer foodId) {
        Food food = foodRepository.findById(foodId).orElse(null);
        if(food == null){
            throw new FoodNotFoundException("Nem található étel");
        }
        return food;
    }
}
