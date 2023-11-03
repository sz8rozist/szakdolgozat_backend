package com.example.fitness.controller;

import com.example.fitness.model.request.WorkoutRequest;
import com.example.fitness.service.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/workout")
@CrossOrigin(value = "*", maxAge = 0)
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("/{userId}/{date}")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@PathVariable Integer userId, @PathVariable LocalDate date, @RequestBody List<WorkoutRequest> workoutRequest){
        workoutService.save(userId, date, workoutRequest);
    }
}
