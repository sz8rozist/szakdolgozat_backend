package com.example.fitness.service;

import com.example.fitness.exception.GuestNotFoundException;
import com.example.fitness.exception.TrainerNotFoundException;
import com.example.fitness.model.DietRecommedation;
import com.example.fitness.model.Guest;
import com.example.fitness.model.Trainer;
import com.example.fitness.repository.DietRecommendationRepository;
import com.example.fitness.repository.GuestRepository;
import com.example.fitness.repository.TrainerRepository;
import org.springframework.stereotype.Service;

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
}
