package com.example.fitness.serviceTest;

import com.example.fitness.model.DietRecommedation;
import com.example.fitness.model.Guest;
import com.example.fitness.model.Trainer;
import com.example.fitness.repository.DietRecommendationRepository;
import com.example.fitness.repository.GuestRepository;
import com.example.fitness.repository.TrainerRepository;
import com.example.fitness.service.DietRecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestDietRecommedationService {
    @Mock
    private DietRecommendationRepository dietRecommendationRepository;
    @Mock
    private GuestRepository guestRepository;
    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private DietRecommendationService dietRecommendationService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testSave() {
        // Arrange
        DietRecommedation dietRecommendation = new DietRecommedation(); // Create a mock DietRecommendation instance
        int guestId = 1;
        int trainerUserId = 2;
        Guest mockGuest = new Guest(); // Create a mock Guest instance
        Trainer mockTrainer = new Trainer(); // Create a mock Trainer instance
        when(guestRepository.findById(guestId)).thenReturn(java.util.Optional.of(mockGuest));
        when(trainerRepository.findByUserId(trainerUserId)).thenReturn(java.util.Optional.of(mockTrainer));
        when(dietRecommendationRepository.save(any(DietRecommedation.class))).thenReturn(dietRecommendation);

        // Act
        DietRecommedation result = dietRecommendationService.save(new DietRecommedation(), guestId, trainerUserId);

        // Assert
        assertNotNull(result);
        //assertEquals(mockGuest, result.getGuest());
        //assertEquals(mockTrainer, result.getTrainer());
        verify(dietRecommendationRepository, times(1)).save(any(DietRecommedation.class));
    }
    @Test
    void testDelete() {
        // Arrange
        int id = 1;
        when(dietRecommendationRepository.findById(id)).thenReturn(java.util.Optional.of(new DietRecommedation()));

        // Act
        assertDoesNotThrow(() -> dietRecommendationService.delete(id));

        // Assert
        verify(dietRecommendationRepository, times(1)).delete(any(DietRecommedation.class));
    }

    @Test
    void testUpdate() {
        // Arrange
        int id = 1;
        DietRecommedation updatedRecommendation = new DietRecommedation(); // Create an updated recommendation
        DietRecommedation existingRecommendation = new DietRecommedation(); // Create an existing recommendation
        when(dietRecommendationRepository.findById(id)).thenReturn(java.util.Optional.of(existingRecommendation));
        when(dietRecommendationRepository.save(existingRecommendation)).thenReturn(updatedRecommendation);

        // Act
        DietRecommedation result = dietRecommendationService.update(id, updatedRecommendation);

        // Assert
        assertNotNull(result);
        assertEquals(updatedRecommendation, result);
        // Add specific assertions based on your expected results
    }

    @Test
    void testGetById() {
        // Arrange
        int id = 1;
        when(dietRecommendationRepository.findById(id)).thenReturn(java.util.Optional.of(new DietRecommedation()));

        // Act
        DietRecommedation result = dietRecommendationService.getById(id);

        // Assert
        assertNotNull(result);
        // Add specific assertions based on your expected results
    }
}
