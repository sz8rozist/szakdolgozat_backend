package com.example.fitness.serviceTest;

import com.example.fitness.model.Guest;
import com.example.fitness.model.Trainer;
import com.example.fitness.repository.GuestRepository;
import com.example.fitness.repository.TrainerRepository;
import com.example.fitness.service.GuestService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestGuestService {
    @Mock
    private GuestRepository guestRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private GuestService guestService;

    @BeforeEach
    public void setUp() {MockitoAnnotations.openMocks(this);}

    @Test
    public void testFindTrainer() {
        // Arrange
        Integer guestId = 1;
        Trainer trainer = new Trainer();
        when(guestRepository.findTrainerByGuestId(guestId)).thenReturn(Optional.of(trainer));

        // Act
        Optional<Trainer> result = guestService.findTrainer(guestId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    public void testGetAll() {
        // Arrange
        List<Guest> guests = new ArrayList<>();
        when(guestRepository.findAll()).thenReturn(guests);

        // Act
        List<Guest> result = guestService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(guests, result);
    }

    @Test
    public void testGetTrainerGuests() {
        // Arrange
        Integer trainerId = 1;
        List<Guest> guests = new ArrayList<>();
        when(guestRepository.getTrainerGuests(trainerId)).thenReturn(guests);

        // Act
        List<Guest> result = guestService.getTrainerGuests(trainerId);

        // Assert
        assertNotNull(result);
        assertEquals(guests, result);
    }
    @Test
    public void testGetById() {
        // Arrange
        int guestId = 1;
        Guest guest = new Guest();
        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));

        // Act
        Guest result = guestService.getById(guestId);

        // Assert
        assertNotNull(result);
        assertEquals(guest, result);
    }
}
