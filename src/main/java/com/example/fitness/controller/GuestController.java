package com.example.fitness.controller;

import com.example.fitness.model.Trainer;
import com.example.fitness.service.GuestService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/guest")
@CrossOrigin(value = "*",maxAge = 0)
public class GuestController {
    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping("/{guestId}")
    public Optional<Trainer> getTrainer(@PathVariable Integer guestId){
        return guestService.findTrainer(guestId);
    }
}
