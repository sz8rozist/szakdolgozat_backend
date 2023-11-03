package com.example.fitness.controller;

import com.example.fitness.model.Exercise;
import com.example.fitness.model.Food;
import com.example.fitness.model.request.WorkoutRequest;
import com.example.fitness.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/exercise")
@CrossOrigin(value = "*", maxAge = 0)
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/{offset}/{pageSize}")
    public Page<Exercise> getAll(@PathVariable int offset, @PathVariable int pageSize){
        return exerciseService.getAllExercise(offset, pageSize);
    }

    @PostMapping
    public Exercise save(@RequestBody Exercise exercise){
        return exerciseService.saveExercise(exercise);
    }

    @GetMapping
    public List<Exercise> getAllWithoutPagination(){
        return exerciseService.getAllWithoutPagination();
    }

    @GetMapping("/{exerciseId}")
    public Exercise getByID(@PathVariable Integer exerciseId){
        return exerciseService.getExerciseById(exerciseId);
    }
}
