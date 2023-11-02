package com.example.fitness.service;

import com.example.fitness.exception.ExerciseNotFoundException;
import com.example.fitness.model.Exercise;
import com.example.fitness.repository.ExerciseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Page<Exercise> getAllExercise(int offset, int pageSize) {
        return exerciseRepository.findAll(PageRequest.of(offset,pageSize));
    }

    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public List<Exercise> getAllWithoutPagination() {
        return exerciseRepository.findAll();
    }

    public Exercise getExerciseById(Integer exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId).orElse(null);
        if(exercise == null){
            throw new ExerciseNotFoundException("A gyakorlat nem található.");
        }
        return exercise;
    }
}
