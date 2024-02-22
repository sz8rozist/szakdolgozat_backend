package com.example.fitness.service;

import com.example.fitness.exception.DietNotFouncException;
import com.example.fitness.exception.FoodNotFoundException;
import com.example.fitness.exception.GuestNotFoundException;
import com.example.fitness.exception.TrainerNotFoundException;
import com.example.fitness.model.*;
import com.example.fitness.model.dto.*;
import com.example.fitness.model.request.DietRequest;
import com.example.fitness.model.request.DietUpdateRequest;
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

    public DietGuestDto getDietByDateAndUserId(Integer guestId, LocalDate dietDate) {
        List<DietGuest> dietGuests = dietGuestRepository.findDietGuestByGuestId(guestId);
        List<DietDto> diets = new ArrayList<>();

        for(DietGuest dg: dietGuests){
            System.out.println(dg.getDiet());
            if(dg.getDiet().getDate().equals(dietDate)){
                DietDto dietDto = new DietDto();
                dietDto.setDietId(dg.getDiet().getId());
                if(dg.getTrainer() != null){
                    dietDto.setTrainerId(dg.getTrainer().getId());
                }
                dietDto.setFat(dg.getDiet().getFood().getFat());
                dietDto.setCalorie(dg.getDiet().getFood().getCalorie());
                dietDto.setCarbonhydrate(dg.getDiet().getFood().getCarbonhydrate());
                dietDto.setProtein(dg.getDiet().getFood().getProtein());
                dietDto.setEated(dg.getDiet().isEated());
                dietDto.setName(dg.getDiet().getFood().getName());
                dietDto.setQuantity(dg.getDiet().getQuantity());
                dietDto.setFoodType(dg.getDiet().getType());
                dietDto.setFoodId(dg.getDiet().getFood().getId());
                diets.add(dietDto);
            }
        }
        int calorieSum = 0;
        int proteinSum = 0;
        int carbonhydrateSum= 0;
        int fatSum = 0;
        for(DietDto d: diets){
            calorieSum += (d.getCalorie() / 100) * d.getQuantity();
            proteinSum +=  (d.getProtein() / 100) * d.getQuantity();
            carbonhydrateSum += (d.getCarbonhydrate() / 100) * d.getQuantity();
            fatSum += (d.getFat() / 100) * d.getQuantity();
        }
        DietGuestDto dietResponse = new DietGuestDto();
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

    public List<DietSummaryDto> getMacronutrienseStatisztics(Integer guestUserId) {
        Guest guest = guestRepository.findByUserId(guestUserId).orElseThrow(() -> new GuestNotFoundException("Vendég nem található: "+ guestUserId));
        return dietRepository.getDietSummary(guest.getId());
    }

    public CaloriesSumDto getCalores(Integer userId) {
        Guest guest = guestRepository.findByUserId(userId).orElseThrow(() -> new GuestNotFoundException("Vendég nem található: "+ userId));
        Double caloriesByDate = dietRepository.getCaloriesSumByDate(guest.getId());
        Double caloriesByWeek = dietRepository.getCaloriesSumForCurrentWeek(guest.getId());
        Double caloriesByMonth = dietRepository.getCaloriesSumForCurrentMonth(guest.getId());
        return new CaloriesSumDto(caloriesByDate != null ? caloriesByDate : 0, caloriesByWeek != null ? caloriesByWeek : 0, caloriesByMonth != null ? caloriesByMonth : 0);
    }

    public List<MealFrequencyDto> getMealFrequency(Integer userId) {
        Guest guest = guestRepository.findByUserId(userId).orElseThrow(() -> new GuestNotFoundException("Vendég nem található: "+ userId));
        return dietRepository.getMealFrequency(guest.getId());
    }

    public List<CalendarEventDto> getAllDietByGuestId(int guestId){
        List<CalendarEventDto> calendarEventDtos = new ArrayList<>();
        List<Diet> diets = dietRepository.getAllDietByGuestId(guestId);
        for(Diet d : diets){
            CalendarEventDto dto = new CalendarEventDto("Étrend", d.getDate(), "#0000fff", false, true, guestId);
            calendarEventDtos.add(dto);
        }
        return calendarEventDtos;
    }

    public List<CalendarEventDto> getAllTrainerGuestDiet(int trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(() -> new TrainerNotFoundException("Nem található edző."));
        List<CalendarEventDto> calendarEventDtos = new ArrayList<>();
        for(Guest guest : trainer.getGuests()){
            List<Diet> diets = dietRepository.getAllDietByGuestId(guest.getId());
            for(Diet d: diets){
                CalendarEventDto dto = new CalendarEventDto(guest.getFirst_name() + " " + guest.getLast_name() + " étrend", d.getDate(), "#0000fff", true, true, guest.getId());
                calendarEventDtos.add(dto);
            }
        }
        return calendarEventDtos;
    }

    public void setEated(int dietId) {
        Diet diet = dietRepository.findById(dietId).orElseThrow(() -> new DietNotFouncException("Nem található étrend"));
        diet.setEated(true);
        dietRepository.save(diet);
    }
}
