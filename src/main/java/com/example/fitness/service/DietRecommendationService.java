package com.example.fitness.service;

import com.example.fitness.exception.DietRecommendationNotFoundException;
import com.example.fitness.exception.GuestNotFoundException;
import com.example.fitness.exception.TrainerNotFoundException;
import com.example.fitness.model.DietRecommedation;
import com.example.fitness.model.Guest;
import com.example.fitness.model.Trainer;
import com.example.fitness.model.dto.NutritionDto;
import com.example.fitness.model.request.NutritionRequest;
import com.example.fitness.repository.DietRecommendationRepository;
import com.example.fitness.repository.GuestRepository;
import com.example.fitness.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DietRecommendationService {
    private final DietRecommendationRepository dietRecommendationRepository;
    private final GuestRepository guestRepository;
    private final TrainerRepository trainerRepository;

    public DietRecommendationService(DietRecommendationRepository dietRecommendationRepository, GuestRepository guestRepository, TrainerRepository trainerRepository) {
        this.dietRecommendationRepository = dietRecommendationRepository;
        this.guestRepository = guestRepository;
        this.trainerRepository = trainerRepository;
    }

    public DietRecommedation save(DietRecommedation dietRecommedation, int guestId, int trainerUserId) {
        Guest guest = guestRepository.findById(guestId).orElse(null);
        Trainer trainer = trainerRepository.findByUserId(trainerUserId).orElse(null);
        if(guest == null){
            throw new GuestNotFoundException("Vendég nem található: " + guestId);
        }
        if(trainer == null){
            throw new TrainerNotFoundException("Edző nem található ezzel a user id-val: " + trainerUserId);
        }
        dietRecommedation.setGuest(guest);
        dietRecommedation.setTrainer(trainer);
        return dietRecommendationRepository.save(dietRecommedation);
    }

    public DietRecommedation getRecommendationByDateAndGuest(int guestUserId, LocalDate date) throws DietRecommendationNotFoundException {
        Guest guest = guestRepository.findByUserId(guestUserId).orElseThrow(() -> new GuestNotFoundException("Ez a vendég nem található."));
        return dietRecommendationRepository.findByGuest_IdAndDate(guest.getId(), date).orElseThrow(() -> new DietRecommendationNotFoundException("Erre a napra nincs tápérték ajánlás: " + date));
    }

    public List<DietRecommedation> getAll(int guestId, int trainerUserId) {
        Trainer trainer = trainerRepository.findByUserId(trainerUserId).orElseThrow(() -> new TrainerNotFoundException("Nem található edző!"));
        return dietRecommendationRepository.getAllRecommendation(guestId, trainer.getId());
    }

    public void delete(int id) {
        DietRecommedation dietRecommedation = dietRecommendationRepository.findById(id).orElseThrow(()-> new DietRecommendationNotFoundException("Nem található étrend ajánlás."));
        dietRecommendationRepository.delete(dietRecommedation);
    }

    public DietRecommedation update(int id, DietRecommedation dietRecommedation) {
        DietRecommedation dietRecommedation1 = dietRecommendationRepository.findById(id).orElseThrow(() -> new DietRecommendationNotFoundException("Nem található étrend ajánlás."));
        dietRecommedation1.setDate(dietRecommedation.getDate());
        dietRecommedation1.setFat(dietRecommedation.getFat());
        dietRecommedation1.setProtein(dietRecommedation.getProtein());
        dietRecommedation1.setCalorie(dietRecommedation.getCalorie());
        dietRecommedation1.setCarbonhydrate(dietRecommedation.getCarbonhydrate());
        return dietRecommendationRepository.save(dietRecommedation1);
    }

    public DietRecommedation getById(int id) {
        return dietRecommendationRepository.findById(id).orElseThrow(()-> new DietRecommendationNotFoundException("Nem található étrend ajánlás."));
    }

    public NutritionDto nutiritonCalculate(NutritionRequest nutritionRequest) {
        Guest guest = guestRepository.findById(nutritionRequest.getGuestId()).orElseThrow(() -> new GuestNotFoundException("Nem található vendég"));
        double basalMetabolicRate = calculateBMR(guest.getAge(), nutritionRequest.getBodyWeight(), guest.isGender(), guest.getHeight());
        // Az átlagértékek számítása a BMR és más tényezők alapján
        double proteinPercentage = (double) nutritionRequest.getProteinSzazalek() / 100;
        double carbohydratePercentage = (double) nutritionRequest.getChSzazalek() / 100;
        double fatPercentage = (double) nutritionRequest.getFatSzazalek() / 100;

        double protein = basalMetabolicRate * proteinPercentage / 4.0;
        double carbohydrates = basalMetabolicRate * carbohydratePercentage / 4.0;
        double fat = basalMetabolicRate * fatPercentage / 9.0;

        NutritionDto result = new NutritionDto();
        result.setProtein((int)Math.round(protein));
        result.setCalories((int)Math.round(basalMetabolicRate));
        result.setCarbohydrates((int)Math.round(carbohydrates));
        result.setFat((int)Math.round(fat));
        return result;
    }

    private double calculateBMR(int age, double weight, boolean gender, double height) {
        // Mifflin-St Jeor képlet alapanyagcsere kalkuláció képlete
        if(gender){
            //Férfi
            return 10 * weight + 6.25 * height - 5 * age + 5;
        }else{
            //Nő
            return 10 * weight + 6.25 * height - 5 * age - 161;
        }
    }
}
