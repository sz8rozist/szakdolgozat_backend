package com.example.fitness.service;

import com.example.fitness.exception.ExerciseNotFoundException;
import com.example.fitness.model.Exercise;
import com.example.fitness.model.Workout;
import com.example.fitness.model.WorkoutGuest;
import com.example.fitness.repository.ExerciseRepository;
import com.example.fitness.repository.WorkoutGuestRepository;
import com.example.fitness.repository.WorkoutRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutGuestRepository workoutGuestRepository;

    public ExerciseService(ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository, WorkoutGuestRepository workoutGuestRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
        this.workoutGuestRepository = workoutGuestRepository;
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

    public void deleteExercise(Integer workoutId, Integer guestId) {
        Optional<Workout> workout = workoutRepository.findById(workoutId);
        Optional<WorkoutGuest> workoutGuest = workoutGuestRepository.findWorkoutGuestByWorkoutIdAndGuestId(workout.get().getId(), guestId);
        workoutGuest.ifPresent(workoutGuestRepository::delete);
        workout.ifPresent(workoutRepository::delete);
    }
}
