package com.example.fitness.controller;

import com.example.fitness.model.Workout;
import com.example.fitness.model.dto.CalendarEventDto;
import com.example.fitness.model.dto.ExerciseRegularityDto;
import com.example.fitness.model.dto.RecentlyUsedExerciseDto;
import com.example.fitness.model.dto.WorkoutDto;
import com.example.fitness.model.request.DietUpdateRequest;
import com.example.fitness.model.request.WorkoutRequest;
import com.example.fitness.model.request.WorkoutUpdateRequest;
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

    @PostMapping("/saveTrainer/{userId}/{guestId}/{date}")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveByTrainer(@PathVariable Integer userId, @PathVariable Integer guestId, @PathVariable LocalDate date, @RequestBody List<WorkoutRequest> workoutRequest){
        workoutService.saveByTrainer(userId, guestId, date, workoutRequest);
    }
    @GetMapping("/{guestId}/{date}")
    public List<WorkoutDto> getWorkouts(@PathVariable Integer guestId, @PathVariable LocalDate date){
        return workoutService.getWorkoutByDateAndUserId(guestId, date);
    }
    @GetMapping("/{id}")
    public Workout getWorkoutById(@PathVariable Integer id){
        return workoutService.getById(id);
    }
    @PutMapping("/{workoutId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDiet(@RequestBody WorkoutUpdateRequest workoutUpdateRequest, @PathVariable Integer workoutId){
        workoutService.updateWorkout(workoutUpdateRequest, workoutId);
    }
    @DeleteMapping("/{guestId}/{date}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWorkout(@PathVariable Integer guestId, @PathVariable LocalDate date){
        workoutService.deleteWorkout(guestId, date);
    }
    @GetMapping("/recentlyUsedExercise/{userId}")
    public List<RecentlyUsedExerciseDto> getRecentlyUsedExercise(@PathVariable Integer userId){
        return workoutService.getRecentlyUsedExercise(userId);
    }
    @GetMapping("/exerciseRegularity/{userId}")
    public List<ExerciseRegularityDto> getExerciseRegularity(@PathVariable Integer userId){
        return workoutService.getExerciseRegularity(userId);
    }
    @GetMapping("/trainerWorkoutPlanCount/{trainerId}")
    public Integer getTrainerWorkoutPlanCount(@PathVariable Integer trainerId){
        return workoutService.getTrainerWorkoutPlanCount(trainerId);
    }

    @GetMapping("/getAllWorkoutByGuestId/{guestId}")
    public List<CalendarEventDto> getAllWorkoutByGuest(@PathVariable int guestId){
        return workoutService.getAllWorkoutByGuestId(guestId);
    }

    @GetMapping("/getAllTrainerGuestWorkout/{trainerId}")
    public List<CalendarEventDto> getAllTrainerGuestWorkout(@PathVariable int trainerId){
        return workoutService.getALlTrainerGuestWorkout(trainerId);
    }
}
