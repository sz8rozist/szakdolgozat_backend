package com.example.fitness.service;

import com.example.fitness.model.Guest;
import com.example.fitness.model.Trainer;
import com.example.fitness.repository.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Optional<Trainer> findTrainer(Integer guestId) {
        Optional<Trainer> trainer = guestRepository.findTrainerByGuestId(guestId);
        return trainer;
    }
}
