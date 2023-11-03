package com.example.fitness.service;

import com.example.fitness.exception.GuestNotFoundException;
import com.example.fitness.model.*;
import com.example.fitness.model.request.WorkoutRequest;
import com.example.fitness.repository.ExerciseRepository;
import com.example.fitness.repository.GuestRepository;
import com.example.fitness.repository.WorkoutGuestRepository;
import com.example.fitness.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final GuestRepository guestRepository;
    private final WorkoutGuestRepository workoutGuestRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutService(WorkoutRepository workoutRepository, GuestRepository guestRepository, WorkoutGuestRepository workoutGuestRepository, ExerciseRepository exerciseRepository) {
        this.workoutRepository = workoutRepository;
        this.guestRepository = guestRepository;
        this.workoutGuestRepository = workoutGuestRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public void save(Integer userId, LocalDate date, List<WorkoutRequest> workoutRequest) {
        Guest guest = guestRepository.findByUserId(userId).orElse(null);
        if(guest == null){
            throw new GuestNotFoundException("Nem található vendég ezzel a userId-val: " + userId);
        }
        List<Workout> workouts = new ArrayList<>();
        List<WorkoutGuest> workoutGuests = new ArrayList<>();
        for(WorkoutRequest wr: workoutRequest){
            Exercise exercise = exerciseRepository.findById(wr.getExerciseId()).orElse(null);
            if(exercise != null){
                Workout workout = new Workout();
                WorkoutGuest workoutGuest = new WorkoutGuest();
                workout.setDate(date);
                workout.setSets(wr.getSets());
                workout.setRepetitions(wr.getRepetitions());
                workout.setExercise(exercise);
                workoutGuest.setGuest(guest);
                workoutGuest.setWorkout(workout);
                workouts.add(workout);
                workoutGuests.add(workoutGuest);
            }
        }
        workoutRepository.saveAll(workouts);
        workoutGuestRepository.saveAll(workoutGuests);
    }
}
