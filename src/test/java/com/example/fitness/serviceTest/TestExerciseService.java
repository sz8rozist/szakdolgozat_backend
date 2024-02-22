package com.example.fitness.serviceTest;

import com.example.fitness.exception.ExerciseNotFoundException;
import com.example.fitness.model.Exercise;
import com.example.fitness.model.Workout;
import com.example.fitness.model.WorkoutGuest;
import com.example.fitness.repository.ExerciseRepository;
import com.example.fitness.repository.WorkoutGuestRepository;
import com.example.fitness.repository.WorkoutRepository;
import com.example.fitness.service.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class TestExerciseService {
    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private WorkoutGuestRepository workoutGuestRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllExercise() {
        // Arrange
        int offset = 0;
        int pageSize = 10;
        List<Exercise> exercises = Arrays.asList(new Exercise(), new Exercise());
        Page<Exercise> exercisePage = new PageImpl<>(exercises);

        when(exerciseRepository.findAll(PageRequest.of(offset, pageSize))).thenReturn(exercisePage);

        // Act
        Page<Exercise> result = exerciseService.getAllExercise(offset, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(exercises, result.getContent());
        verify(exerciseRepository, times(1)).findAll(PageRequest.of(offset, pageSize));
    }

    @Test
    public void testSaveExercise() {
        // Arrange
        Exercise exerciseToSave = new Exercise();
        when(exerciseRepository.save(exerciseToSave)).thenReturn(exerciseToSave);

        // Act
        Exercise result = exerciseService.saveExercise(exerciseToSave);

        // Assert
        assertNotNull(result);
        assertEquals(exerciseToSave, result);
        verify(exerciseRepository, times(1)).save(exerciseToSave);
    }

    @Test
    public void testGetAllWithoutPagination() {
        // Arrange
        List<Exercise> exercises = Arrays.asList(new Exercise(), new Exercise());

        when(exerciseRepository.findAll()).thenReturn(exercises);

        // Act
        List<Exercise> result = exerciseService.getAllWithoutPagination();

        // Assert
        assertNotNull(result);
        assertEquals(exercises, result);
        verify(exerciseRepository, times(1)).findAll();
    }

    @Test
    public void testGetExerciseById() {
        // Arrange
        Integer exerciseId = 1;
        Exercise exercise = new Exercise();
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));

        // Act
        Exercise result = exerciseService.getExerciseById(exerciseId);

        // Assert
        assertNotNull(result);
        assertEquals(exercise, result);
        verify(exerciseRepository, times(1)).findById(exerciseId);
    }

    @Test
    public void testGetExerciseByIdNotFound() {
        // Arrange
        Integer exerciseId = 1;
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ExerciseNotFoundException.class, () -> exerciseService.getExerciseById(exerciseId));
    }

    @Test
    public void testDeleteExercise() {
        // Arrange
        Integer workoutId = 1;
        Integer guestId = 2;

        Workout workout = new Workout();
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));

        WorkoutGuest workoutGuest = new WorkoutGuest();
        when(workoutGuestRepository.findWorkoutGuestByWorkoutIdAndGuestId(workout.getId(), guestId)).thenReturn(Optional.of(workoutGuest));

        // Act
        assertDoesNotThrow(() -> exerciseService.deleteExercise(workoutId, guestId));

        // Assert
        verify(workoutGuestRepository, times(1)).delete(workoutGuest);
        verify(workoutRepository, times(1)).delete(workout);
    }
}
