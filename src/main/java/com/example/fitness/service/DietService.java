package com.example.fitness.service;

import com.example.fitness.exception.DietNotFouncException;
import com.example.fitness.exception.FoodNotFoundException;
import com.example.fitness.model.*;
import com.example.fitness.model.request.DietRequest;
import com.example.fitness.model.request.DietUpdateRequest;
import com.example.fitness.model.dto.DietDto;
import com.example.fitness.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DietService {
    private final DietRepository dietRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final TrainerRepository trainerRepository;
    private final DietGuestRepository dietGuestRepository;
    private final GuestRepository guestRepository;

    public DietService(DietRepository dietRepository, FoodRepository foodRepository, UserRepository userRepository, TrainerRepository trainerRepository, DietGuestRepository dietGuestRepository, GuestRepository guestRepository) {
        this.dietRepository = dietRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
        this.trainerRepository = trainerRepository;
        this.dietGuestRepository = dietGuestRepository;
        this.guestRepository = guestRepository;
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
                Optional<Guest> guest = guestRepository.findById(d.getGuestId());
                guest.ifPresent(dietGuest::setGuest);
                if(d.getTrainerId() != null){
                    Optional<Trainer> trainer = trainerRepository.findById(d.getTrainerId());
                    trainer.ifPresent(dietGuest::setTrainer);
                }
                diets.add(diet);
                dietGuests.add(dietGuest);
            }
        }
        dietRepository.saveAll(diets);
        dietGuestRepository.saveAll(dietGuests);
    }

    public DietDto getDietByDateAndUserId(Integer guestId, LocalDate dietDate) {
        List<Diet> diets = dietRepository.findDietsByGuestIdAndDietDate(guestId,dietDate);
        int calorieSum = 0;
        int proteinSum = 0;
        int carbonhydrateSum= 0;
        int fatSum = 0;
        for(Diet d: diets){
            calorieSum += d.getFood().getCalorie();
            proteinSum += (int) d.getFood().getProtein();
            carbonhydrateSum += (int) d.getFood().getCarbonhydrate();
            fatSum += (int) d.getFood().getFat();
        }
        DietDto dietResponse = new DietDto();
        dietResponse.setDiet(diets);
        dietResponse.setCalorieSum(calorieSum);
        dietResponse.setProteinSum(proteinSum);
        dietResponse.setCarbonhydrateSum(carbonhydrateSum);
        dietResponse.setFatSum(fatSum);
        return dietResponse;
    }

    public void deleteDiet(Integer guestId, LocalDate dietDate) {
        List<Diet> diets = dietRepository.findDietsByGuestIdAndDietDate(guestId, dietDate);
        for(Diet d: diets){
            Optional<DietGuest> dietGuest = dietGuestRepository.findDietGuestByDietIdAndGuestId(d.getId(), guestId);
            dietGuest.ifPresent(dietGuestRepository::delete);
        }
        dietRepository.deleteAll(diets);
    }

    public void deleteFood(Integer dietId, Integer guestId) {
        Optional<Diet> diet = dietRepository.findById(dietId);
        Optional<DietGuest> dietGuest = dietGuestRepository.findDietGuestByDietIdAndGuestId(diet.get().getId(), guestId);
        dietGuest.ifPresent(dietGuestRepository::delete);
        diet.ifPresent(dietRepository::delete);
    }

    public Diet getDietById(Integer dietId) {
       Diet diet = dietRepository.findById(dietId).orElse(null);
        if(diet == null){
            throw new DietNotFouncException("Nem található étrend");
        }
        return diet;
    }

    public void updateDiet(DietUpdateRequest dietUpdateRequest, Integer dietId) {
        Food food = foodRepository.findById(dietUpdateRequest.getFoodId()).orElse(null);
        Diet diet = dietRepository.findById(dietId).orElse(null);
        if(food == null){
            throw new FoodNotFoundException("Nem található étel");
        }
        if(diet == null){
            throw new DietNotFouncException("Nem található étkezés");
        }
        diet.setFood(food);
        diet.setDate(dietUpdateRequest.getDate());
        diet.setQuantity(dietUpdateRequest.getQuantity());
        diet.setType(dietUpdateRequest.getType());
        dietRepository.save(diet);
    }
}
