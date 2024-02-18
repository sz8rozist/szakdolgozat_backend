package com.example.fitness.serviceTest;

import com.example.fitness.model.*;
import com.example.fitness.model.dto.CalendarEventDto;
import com.example.fitness.model.dto.ExerciseRegularityDto;
import com.example.fitness.model.dto.RecentlyUsedExerciseDto;
import com.example.fitness.model.dto.WorkoutDto;
import com.example.fitness.model.request.WorkoutRequest;
import com.example.fitness.model.request.WorkoutUpdateRequest;
import com.example.fitness.repository.*;
import com.example.fitness.service.WorkoutService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class TestWorkoutService {
    WorkoutRepository workoutRepository = mock(WorkoutRepository.class);

    GuestRepository guestRepository = mock(GuestRepository.class);

    WorkoutGuestRepository workoutGuestRepository = mock(WorkoutGuestRepository.class);

    ExerciseRepository exerciseRepository = mock(ExerciseRepository.class);

    TrainerRepository trainerRepository = mock(TrainerRepository.class);


    @Test
    public void testSaveWorkout() {
        // Arrange
        WorkoutService workoutService = new WorkoutService(workoutRepository, guestRepository, workoutGuestRepository, exerciseRepository, trainerRepository);
        int userId = 1;
        LocalDate date = LocalDate.now();
        List<WorkoutRequest> workoutRequests = new ArrayList<>();
        // Add workout requests to the list...

        // Mock guest retrieval
        Guest guest = new Guest();
        when(guestRepository.findByUserId(userId)).thenReturn(Optional.of(guest));

        // Mock exercise retrieval
        Exercise exercise = new Exercise();
        when(exerciseRepository.findById(anyInt())).thenReturn(Optional.of(exercise));

        // Act
        assertDoesNotThrow(() -> workoutService.save(userId, date, workoutRequests));

        // Assert or verify specific behavior as needed
    }

    @Test
    public void testGetById() {
        // Arrange
        WorkoutService workoutService = new WorkoutService(workoutRepository, guestRepository, workoutGuestRepository, exerciseRepository, trainerRepository);
        int workoutId = 1;

        // Mock workout retrieval
        Workout expectedWorkout = new Workout();
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(expectedWorkout));

        // Act
        Workout result = workoutService.getById(workoutId);

        // Assert
        assertEquals(expectedWorkout, result);
    }

    @Test
    public void testUpdateWorkout() {
        // Arrange
        WorkoutService workoutService = new WorkoutService(workoutRepository, guestRepository, workoutGuestRepository, exerciseRepository, trainerRepository);
        int workoutId = 1;
        WorkoutUpdateRequest workoutUpdateRequest = new WorkoutUpdateRequest(10,4,LocalDate.now(), 1);

        // Mock exercise retrieval
        Exercise exercise = new Exercise();
        when(exerciseRepository.findById(workoutUpdateRequest.getExerciseId())).thenReturn(Optional.of(exercise));

        // Mock workout retrieval
        Workout existingWorkout = new Workout();
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(existingWorkout));

        // Act
        assertDoesNotThrow(() -> workoutService.updateWorkout(workoutUpdateRequest, workoutId));

        // Assert or verify specific behavior as needed
    }

    @Test
    public void testDeleteWorkout() {
        // Arrange
        WorkoutService workoutService = new WorkoutService(workoutRepository, guestRepository, workoutGuestRepository, exerciseRepository, trainerRepository);
        int guestId = 1;
        LocalDate workoutDate = LocalDate.now();

        // Mock workout retrieval
        List<WorkoutDto> workouts = new ArrayList<>();
        WorkoutDto workoutDto = new WorkoutDto();
        workouts.add(workoutDto);
        when(workoutRepository.findWorkoutsByGuestIdAndWorkoutDate(guestId, workoutDate)).thenReturn(workouts);

        // Mock workout and workoutGuest deletion
        Workout workout = new Workout();
        WorkoutGuest workoutGuest = new WorkoutGuest();
        when(workoutRepository.findById(workoutDto.getWorkoutId())).thenReturn(Optional.of(workout));
        when(workoutGuestRepository.findWorkoutGuestByWorkoutIdAndGuestId(workoutDto.getWorkoutId(), guestId)).thenReturn(Optional.of(workoutGuest));

        // Act
        assertDoesNotThrow(() -> workoutService.deleteWorkout(guestId, workoutDate));
    }

    @Test
    public void testGetRecentlyUsedExercise() {
        // Arrange
        int userId = 1;
        WorkoutService workoutService = new WorkoutService(workoutRepository, guestRepository, workoutGuestRepository, exerciseRepository, trainerRepository);

        Guest guest = new Guest();
        guest.setId(1);
        when(guestRepository.findByUserId(userId)).thenReturn(java.util.Optional.of(guest));
        when(workoutRepository.findRecentlyUsedExercise(guest.getId())).thenReturn(List.of());

        // Act
        List<RecentlyUsedExerciseDto> result = workoutService.getRecentlyUsedExercise(userId);

        // Assert
        assertNotNull(result);
        // Add more specific assertions based on your actual data and expectations
    }

    @Test
    public void testGetExerciseRegularity() {
        // Arrange
        int userId = 1;
        WorkoutService workoutService = new WorkoutService(workoutRepository, guestRepository, workoutGuestRepository, exerciseRepository, trainerRepository);

        Guest guest = new Guest();
        guest.setId(1);

        when(guestRepository.findByUserId(userId)).thenReturn(java.util.Optional.of(guest));

        when(workoutRepository.findExerciseRegularity(guest.getId())).thenReturn(List.of());

        // Act
        List<ExerciseRegularityDto> result = workoutService.getExerciseRegularity(userId);

        // Assert
        assertNotNull(result);
        // Add more specific assertions based on your actual data and expectations
    }

    @Test
    public void testGetTrainerWorkoutPlanCount() {
        // Arrange
        int trainerId = 1;
        WorkoutService workoutService = new WorkoutService(workoutRepository, guestRepository, workoutGuestRepository, exerciseRepository, trainerRepository);

        when(workoutGuestRepository.countTrainerWorkoutPlan(trainerId)).thenReturn(5);

        // Act
        Integer result = workoutService.getTrainerWorkoutPlanCount(trainerId);

        // Assert
        assertEquals(5, result.intValue());
    }

    @Test
    public void testGetAllWorkoutByGuestId() {
        // Arrange
        int guestId = 1;
        WorkoutService workoutService = new WorkoutService(workoutRepository, guestRepository, workoutGuestRepository, exerciseRepository, trainerRepository);

        List<Workout> workouts = Arrays.asList(new Workout(/*...*/), new Workout(/*...*/));
        when(workoutRepository.getAllWorkoutByGuest(guestId)).thenReturn(workouts);

        // Act
        List<CalendarEventDto> result = workoutService.getAllWorkoutByGuestId(guestId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        // Add more specific assertions based on your actual data and expectations
    }

    @Test
    public void testGetGuestById() {
        // Arrange
        WorkoutService workoutService = new WorkoutService(workoutRepository, guestRepository, workoutGuestRepository, exerciseRepository, trainerRepository);
        int guestId = 1;
        Guest guest = new Guest(/*...*/);
        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));

        // Act
        Guest result = workoutService.getGuestById(guestId);

        // Assert
        assertNotNull(result);
        // Add more specific assertions based on your actual data and expectations
    }

    @Test
    public void testGetTrainerByUserId() {
        // Arrange
        WorkoutService workoutService = new WorkoutService(workoutRepository, guestRepository, workoutGuestRepository, exerciseRepository, trainerRepository);
        int userId = 1;
        Trainer trainer = new Trainer(/*...*/);
        when(trainerRepository.findByUserId(userId)).thenReturn(Optional.of(trainer));

        // Act
        Trainer result = workoutService.getTrainerByUserId(userId);

        // Assert
        assertNotNull(result);
        // Add more specific assertions based on your actual data and expectations
    }

    @Test
    public void testGetExerciseById() {
        // Arrange
        WorkoutService workoutService = new WorkoutService(workoutRepository, guestRepository, workoutGuestRepository, exerciseRepository, trainerRepository);
        int exerciseId = 1;
        Exercise exercise = new Exercise(/*...*/);
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));

        // Act
        Exercise result = workoutService.getExerciseById(exerciseId);

        // Assert
        assertNotNull(result);
        // Add more specific assertions based on your actual data and expectations
    }

    @Test
    public void testCreateWorkoutGuest() {
        // Arrange
        WorkoutService workoutService = new WorkoutService(workoutRepository, guestRepository, workoutGuestRepository, exerciseRepository, trainerRepository);
        Guest guest = new Guest(/*...*/);
        Trainer trainer = new Trainer(/*...*/);
        Exercise exercise = new Exercise(/*...*/);
        Workout workout = new Workout(/*...*/);

        // Act
        WorkoutGuest result = workoutService.createWorkoutGuest(guest, trainer, workout);

        // Assert
        assertNotNull(result);
        assertEquals(guest, result.getGuest());
        assertEquals(trainer, result.getTrainer());
        assertEquals(workout, result.getWorkout());
    }

    @Test
    public void testSaveWorkoutsAndWorkoutGuests() {
        // Arrange
        WorkoutService workoutService = new WorkoutService(workoutRepository, guestRepository, workoutGuestRepository, exerciseRepository, trainerRepository);
        List<Workout> workouts = Arrays.asList(new Workout(/*...*/), new Workout(/*...*/));
        List<WorkoutGuest> workoutGuests = Arrays.asList(new WorkoutGuest(/*...*/), new WorkoutGuest(/*...*/));

        // Act
        workoutService.saveWorkoutsAndWorkoutGuests(workouts, workoutGuests);

        // Assert
        // Add assertions to check if the save operations were called as expected
        verify(workoutRepository, times(1)).saveAll(workouts);
        verify(workoutGuestRepository, times(1)).saveAll(workoutGuests);
    }

}
