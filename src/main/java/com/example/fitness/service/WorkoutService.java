package com.example.fitness.service;

import com.example.fitness.exception.*;
import com.example.fitness.model.*;
import com.example.fitness.model.dto.CalendarEventDto;
import com.example.fitness.model.dto.ExerciseRegularityDto;
import com.example.fitness.model.dto.RecentlyUsedExerciseDto;
import com.example.fitness.model.dto.WorkoutDto;
import com.example.fitness.model.request.WorkoutRequest;
import com.example.fitness.model.request.WorkoutUpdateRequest;
import com.example.fitness.repository.*;
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
    private final TrainerRepository trainerRepository;

    public WorkoutService(WorkoutRepository workoutRepository, GuestRepository guestRepository, WorkoutGuestRepository workoutGuestRepository, ExerciseRepository exerciseRepository, TrainerRepository trainerRepository) {
        this.workoutRepository = workoutRepository;
        this.guestRepository = guestRepository;
        this.workoutGuestRepository = workoutGuestRepository;
        this.exerciseRepository = exerciseRepository;
        this.trainerRepository = trainerRepository;
    }

    public void save(Integer userId, LocalDate date, List<WorkoutRequest> workoutRequest) {
        Guest guest = guestRepository.findByUserId(userId).orElse(null);
        if(guest == null){
            throw new GuestNotFoundException("Nem található vendég ezzel a userId-val: " + userId);
        }
        List<Workout> workouts = new ArrayList<>();
        List<WorkoutGuest> workoutGuests = new ArrayList<>();

        for (WorkoutRequest wr : workoutRequest) {
            Exercise exercise = getExerciseById(wr.getExerciseId());

            if (exercise != null) {
                Workout workout = createWorkout(date, wr.getSets(), wr.getRepetitions(), exercise);
                WorkoutGuest workoutGuest = createWorkoutGuest(guest, null, workout);

                workouts.add(workout);
                workoutGuests.add(workoutGuest);
            }
        }

        saveWorkoutsAndWorkoutGuests(workouts, workoutGuests);
    }

    public List<WorkoutDto> getWorkoutByDateAndUserId(Integer guestId, LocalDate date){
        Guest guest = guestRepository.findById(guestId).orElse(null);
        if(guest == null){
            throw new GuestNotFoundException("Nem található vendég ezzel az azonosítóval: " + guestId);
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
        List<WorkoutDto> workouts = workoutRepository.findWorkoutsByGuestIdAndWorkoutDate(guestId, workoutDate);
        for(WorkoutDto w: workouts){
            Optional<WorkoutGuest> workoutGuest = workoutGuestRepository.findWorkoutGuestByWorkoutIdAndGuestId(w.getWorkoutId(), guestId);
            Optional<Workout> workout = workoutRepository.findById(w.getWorkoutId());
            workoutGuest.ifPresent(workoutGuestRepository::delete);
            workout.ifPresent(workoutRepository::delete);
        }
    }

    public void saveByTrainer(Integer userId, Integer guestId, LocalDate date, List<WorkoutRequest> workoutRequest) {
        Guest guest = getGuestById(guestId);
        Trainer trainer = getTrainerByUserId(userId);

        List<Workout> workouts = new ArrayList<>();
        List<WorkoutGuest> workoutGuests = new ArrayList<>();

        for (WorkoutRequest wr : workoutRequest) {
            Exercise exercise = getExerciseById(wr.getExerciseId());

            if (exercise != null) {
                Workout workout = createWorkout(date, wr.getSets(), wr.getRepetitions(), exercise);
                WorkoutGuest workoutGuest = createWorkoutGuest(guest, trainer, workout);

                workouts.add(workout);
                workoutGuests.add(workoutGuest);
            }
        }

        saveWorkoutsAndWorkoutGuests(workouts, workoutGuests);
    }

    public Guest getGuestById(Integer guestId) {
        return guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException("Nem található vendég ezzel a id-val: " + guestId));
    }

    public Trainer getTrainerByUserId(Integer userId) {
        return trainerRepository.findByUserId(userId)
                .orElseThrow(() -> new TrainerNotFoundException("Nem található edző: " + userId));
    }

    public Exercise getExerciseById(Integer exerciseId) {
        return exerciseRepository.findById(exerciseId).orElse(null);
    }

    public Workout createWorkout(LocalDate date, int sets, int repetitions, Exercise exercise) {
        Workout workout = new Workout();
        workout.setDate(date);
        workout.setSets(sets);
        workout.setRepetitions(repetitions);
        workout.setExercise(exercise);
        return workout;
    }

    public WorkoutGuest createWorkoutGuest(Guest guest, Trainer trainer, Workout workout) {
        WorkoutGuest workoutGuest = new WorkoutGuest();
        workoutGuest.setGuest(guest);
        if(trainer != null){
            workoutGuest.setTrainer(trainer);
        }
        workoutGuest.setWorkout(workout);
        return workoutGuest;
    }

    public void saveWorkoutsAndWorkoutGuests(List<Workout> workouts, List<WorkoutGuest> workoutGuests) {
        workoutRepository.saveAll(workouts);
        workoutGuestRepository.saveAll(workoutGuests);
    }

    public List<RecentlyUsedExerciseDto> getRecentlyUsedExercise(Integer userID) {
        Guest guest = guestRepository.findByUserId(userID).orElseThrow(()-> new GuestNotFoundException("Nem található vendég"));
        return workoutRepository.findRecentlyUsedExercise(guest.getId());
    }

    public List<ExerciseRegularityDto> getExerciseRegularity(Integer userId) {
        Guest guest = guestRepository.findByUserId(userId).orElseThrow(()-> new GuestNotFoundException("Nem található vendég"));
        return workoutRepository.findExerciseRegularity(guest.getId());
    }

    public Integer getTrainerWorkoutPlanCount(Integer trainerId) {
        return workoutGuestRepository.countTrainerWorkoutPlan(trainerId);
    }

    public List<CalendarEventDto> getAllWorkoutByGuestId(int guestId){
        List<CalendarEventDto> calendarEventDtos = new ArrayList<>();
        List<Workout> workout = workoutRepository.getAllWorkoutByGuest(guestId);
        for(Workout w : workout){
            CalendarEventDto dto = new CalendarEventDto("Edzés", w.getDate(), "#65b741", false, false, guestId);
            calendarEventDtos.add(dto);
        }
        return calendarEventDtos;
    }

    public List<CalendarEventDto> getALlTrainerGuestWorkout(int trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(() -> new TrainerNotFoundException("Nem található edző."));
        List<CalendarEventDto> calendarEventDtos = new ArrayList<>();
        for(Guest guest : trainer.getGuests()){
            List<Workout> workouts = workoutRepository.getAllWorkoutByGuest(guest.getId());
            for(Workout w: workouts){
                CalendarEventDto dto = new CalendarEventDto(guest.getFirst_name() + " " + guest.getLast_name() + " edzés", w.getDate(), "#65b741", true, false, guest.getId());
                calendarEventDtos.add(dto);
            }
        }
        return calendarEventDtos;
    }

    public void setDone(int workoutId) {
        Workout workout = workoutRepository.findById(workoutId).orElseThrow(() -> new WorkoutNotFoundException("Nem található edzés"));
        workout.setDone(true);
        workoutRepository.save(workout);
    }
}
