package com.example.fitness.service;

import com.example.fitness.exception.*;
import com.example.fitness.model.*;
import com.example.fitness.model.request.WorkoutRequest;
import com.example.fitness.model.request.WorkoutUpdateRequest;
import com.example.fitness.repository.ExerciseRepository;
import com.example.fitness.repository.GuestRepository;
import com.example.fitness.repository.WorkoutGuestRepository;
import com.example.fitness.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Workout> getWorkoutByDateAndUserId(Integer userId, LocalDate date){
        Guest guest = guestRepository.findByUserId(userId).orElse(null);
        if(guest == null){
            throw new GuestNotFoundException("Nem található vendég ezzel a user azonosítóval: " + userId);
        }
        return workoutRepository.findWorkoutsByGuestIdAndWorkoutDate(guest.getId(), date);
    }

    public Workout getById(Integer id) {
        Workout workout = workoutRepository.findById(id).orElse(null);
        if(workout == null){
            throw new WorkoutNotFoundException("A " + id + " azonosítójú edzés nem található.");
        }
        return workout;
    }

    public void updateWorkout(WorkoutUpdateRequest workoutUpdateRequest, Integer workoutId) {
        Exercise exercise = exerciseRepository.findById(workoutUpdateRequest.getExerciseId()).orElse(null);
        Workout workout = workoutRepository.findById(workoutId).orElse(null);
        if(exercise == null){
            throw new ExerciseNotFoundException("Nem található gyakorlat");
        }
        if(workout == null){
            throw new WorkoutNotFoundException("Nem található edzés");
        }
        workout.setExercise(exercise);
        workout.setDate(workoutUpdateRequest.getDate());
        workout.setRepetitions(workoutUpdateRequest.getRepetitions());
        workout.setSets(workoutUpdateRequest.getSets());
        workoutRepository.save(workout);
    }

    public void deleteWorkout(Integer guestId, LocalDate workoutDate) {
        List<Workout> workouts = workoutRepository.findWorkoutsByGuestIdAndWorkoutDate(guestId, workoutDate);
        for(Workout w: workouts){
            Optional<WorkoutGuest> workoutGuest = workoutGuestRepository.findWorkoutGuestByWorkoutIdAndGuestId(w.getId(), guestId);
            workoutGuest.ifPresent(workoutGuestRepository::delete);
        }
        workoutRepository.deleteAll(workouts);
    }
}
