package com.example.fitness.service;

import com.example.fitness.model.*;
import com.example.fitness.model.request.DietRequest;
import com.example.fitness.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DietService {
    private final DietRepository dietRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final DietGuestRepository dietGuestRepository;

    public DietService(DietRepository dietRepository, FoodRepository foodRepository, UserRepository userRepository, TrainerRepository trainerRepository, DietGuestRepository dietGuestRepository) {
        this.dietRepository = dietRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.dietGuestRepository = dietGuestRepository;
    }
    public void saveDiet(List<DietRequest> dietFoodList) {
        List<Diet> diets = new ArrayList<>();
        List<DietGuest> dietGuests = new ArrayList<>();
        for(DietRequest d: dietFoodList){
            Food food = foodRepository.findById(d.getFoodId()).orElse(null);
            if(food != null){
                Diet diet = new Diet();
                DietGuest dietGuest = new DietGuest();
                diet.setQuantity(d.getQuantity());
                diet.setDate(LocalDate.parse(d.getDate()));
                diet.setType(FoodType.valueOf(d.getType().name()));
                diet.setFood(food);
                dietGuest.setDiet(diet);
                dietGuest.setGuest(userRepository.findById(d.getUserId()).get().getGuest().get());
                diets.add(diet);
                dietGuests.add(dietGuest);
            }
        }
        dietRepository.saveAll(diets);
        dietGuestRepository.saveAll(dietGuests);
    }
}
