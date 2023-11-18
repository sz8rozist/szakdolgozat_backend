package com.example.fitness.controller;

import com.example.fitness.model.Trainer;
import com.example.fitness.model.request.ChooseTrainerRequest;
import com.example.fitness.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainer")
@CrossOrigin(value = "*",maxAge = 0)
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @GetMapping
    public List<Trainer> getAllTrainer(){
        return trainerService.getAllTrainer();
    }

    @PostMapping
    public void chooseTrainer(@RequestBody ChooseTrainerRequest chooseTrainerRequest){
        trainerService.choose(chooseTrainerRequest);
    }
}
