package com.example.fitness.serviceTest;

import com.example.fitness.model.*;
import com.example.fitness.model.dto.DietGuestDto;
import com.example.fitness.model.request.DietRequest;
import com.example.fitness.repository.*;
import com.example.fitness.service.DietService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class TestDietService {
    @Mock
    private DietRepository dietRepository;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private DietGuestRepository dietGuestRepository;

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private DietService dietService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveDiet_Success() {
        // Arrange
        DietRequest dietRequest = new DietRequest();

        Food food = new Food();

        Guest guest = new Guest();

        when(foodRepository.findById(anyInt())).thenReturn(Optional.of(food));
        when(guestRepository.findById(anyInt())).thenReturn(Optional.of(guest));

        List<DietRequest> dietRequests = new ArrayList<>();
        dietRequest.setDate(String.valueOf(LocalDate.now()));
        dietRequest.setType(FoodType.DINNER);
        dietRequest.setQuantity(200);
        dietRequests.add(dietRequest);
        assertDoesNotThrow(() -> dietService.saveDiet(dietRequests));

    }
    @Test
    void deleteDiet_Success() {
        int guestId = 1;
        LocalDate dietDate = LocalDate.now();
        List<Diet> diets = Arrays.asList(new Diet(), new Diet());
        when(dietRepository.findDietsByGuestIdAndDietDate(anyInt(), any(LocalDate.class))).thenReturn(diets);

        assertDoesNotThrow(() -> dietService.deleteDiet(guestId, dietDate));

    }
}
