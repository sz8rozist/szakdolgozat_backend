package com.example.fitness.controller;

import com.example.fitness.model.Guest;
import com.example.fitness.model.Trainer;
import com.example.fitness.service.GuestService;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('GUEST')")
    public Optional<Trainer> getTrainer(@PathVariable Integer guestId){
        return guestService.findTrainer(guestId);
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('GUEST', 'TRAINER')")
    public List<Guest> getAllGuest(){
        return guestService.getAll();
    }
    @GetMapping("/{trainerId}/getTrainerGuests")
    @PreAuthorize("hasAuthority('TRAINER')")
    public List<Guest> getTrainerGuests(@PathVariable Integer trainerId){
        return guestService.getTrainerGuests(trainerId);
    }
    @PostMapping("/addTrainerToGuest")
    @PreAuthorize("hasAuthority('TRAINER')")
    public Guest addTrainerToGuest(@RequestParam("trainerId") Integer trainerId, @RequestParam("guestId") Integer guestId){
        return guestService.addTrainerToGuest(trainerId, guestId);
    }

    @GetMapping("/guestById/{guestId}")
    @PreAuthorize("hasAnyAuthority('TRAINER', 'GUEST')")
    public Guest getGuestById(@PathVariable int guestId){
        return guestService.getById(guestId);
    }
}
