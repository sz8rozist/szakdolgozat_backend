package com.example.fitness.controller;

import com.example.fitness.model.Guest;
import com.example.fitness.model.Trainer;
import com.example.fitness.service.GuestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @GetMapping
    public List<Guest> getAllGuest(){
        return guestService.getAll();
    }
    @GetMapping("/{trainerId}/getTrainerGuests")
    public List<Guest> getTrainerGuests(@PathVariable Integer trainerId){
        return guestService.getTrainerGuests(trainerId);
    }
    @PostMapping("/addTrainerToGuest")
    public Guest addTrainerToGuest(@RequestParam("trainerId") Integer trainerId, @RequestParam("guestId") Integer guestId){
        return guestService.addTrainerToGuest(trainerId, guestId);
    }
}
