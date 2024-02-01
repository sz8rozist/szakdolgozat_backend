package com.example.fitness.service;

import com.example.fitness.exception.GuestNotFoundException;
import com.example.fitness.exception.TrainerNotFoundException;
import com.example.fitness.model.Guest;
import com.example.fitness.model.Trainer;
import com.example.fitness.repository.GuestRepository;
import com.example.fitness.repository.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService {
    private final GuestRepository guestRepository;
    private final TrainerRepository trainerRepository;

    public GuestService(GuestRepository guestRepository, TrainerRepository trainerRepository) {
        this.guestRepository = guestRepository;
        this.trainerRepository = trainerRepository;
    }

    public Optional<Trainer> findTrainer(Integer guestId) {
        Optional<Trainer> trainer = guestRepository.findTrainerByGuestId(guestId);
        return trainer;
    }
    public List<Guest> getAll() {
        return guestRepository.findAll();
    }

    public List<Guest> getTrainerGuests(Integer trainerId) {
        return guestRepository.getTrainerGuests(trainerId);
    }

    public Guest addTrainerToGuest(Integer trainerId, Integer guestId) {
        Guest guest = guestRepository.findById(guestId).orElseThrow(() -> new GuestNotFoundException("Vendég nem található"));
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(() -> new TrainerNotFoundException("Edző nem található"));
        guest.setTrainer(trainer);
       return guestRepository.save(guest);
    }

    public Guest getById(int guestId) {
        return guestRepository.findById(guestId).orElseThrow(()-> new GuestNotFoundException("Nem található vendég"));
    }
}
