package com.example.fitness.serviceTest;

import com.example.fitness.model.*;
import com.example.fitness.model.dto.*;
import com.example.fitness.model.request.DietRequest;
import com.example.fitness.model.request.DietUpdateRequest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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

    @Test
    void testGetDietByDateAndUserId() {
        // Arrange
        Integer guestId = 1;
        LocalDate dietDate = LocalDate.now();
        List<DietGuest> mockDietGuests = createMockDietGuests();
        when(dietGuestRepository.findDietGuestByGuestId(guestId)).thenReturn(mockDietGuests);

        // Act
        DietGuestDto result = dietService.getDietByDateAndUserId(guestId, dietDate);

        // Assert
        assertEquals(1, result.getDiet().size());
        assertEquals(0, result.getCalorieSum());
        assertEquals(0, result.getProteinSum());
        assertEquals(0, result.getCarbonhydrateSum());
        assertEquals(0, result.getFatSum());
    }

    private List<DietGuest> createMockDietGuests() {
        List<DietGuest> mockDietGuests = new ArrayList<>();

        Diet mockDiet1 = new Diet();
        mockDiet1.setId(1);
        Food food = new Food();
        food.setName("TESZT");
        food.setCalorie(20);
        food.setFat(20);
        food.setProtein(210);
        food.setCarbonhydrate(20);
        food.setId(1);
        mockDiet1.setDate(LocalDate.now());
        mockDiet1.setEated(false);
        mockDiet1.setType(FoodType.DINNER);
        food.setDiets(List.of(mockDiet1));
        mockDiet1.setFood(food);
        DietGuest mockDietGuest1 = new DietGuest();
        mockDietGuest1.setDiet(mockDiet1);
        mockDietGuest1.setTrainer(null);


        mockDietGuests.add(mockDietGuest1);

        return mockDietGuests;
    }
    @Test
    void testGetDietById() {
        // Arrange
        Integer dietId = 1;
        Diet mockDiet = new Diet(); // Create a mock Diet instance

        when(dietRepository.findById(dietId)).thenReturn(Optional.of(mockDiet));

        // Act
        Diet result = dietService.getDietById(dietId);

        // Assert
        assertNotNull(result);
        assertEquals(mockDiet, result);
    }

    @Test
    void testUpdateDiet() {
        // Arrange
        Integer dietId = 1;
        DietUpdateRequest updateRequest = new DietUpdateRequest(); // Create a mock update request
        updateRequest.setFoodId(2);

        Diet mockDiet = new Diet(); // Create a mock Diet instance
        Food mockFood = new Food(); // Create a mock Food instance

        when(foodRepository.findById(updateRequest.getFoodId())).thenReturn(Optional.of(mockFood));
        when(dietRepository.findById(dietId)).thenReturn(Optional.of(mockDiet));

        // Act
        dietService.updateDiet(updateRequest, dietId);

        // Assert
        verify(dietRepository, times(1)).save(any(Diet.class));
        assertEquals(mockFood, mockDiet.getFood());
        assertEquals(updateRequest.getDate(), mockDiet.getDate());
        assertEquals(updateRequest.getQuantity(), mockDiet.getQuantity());
        assertEquals(updateRequest.getType(), mockDiet.getType());
    }

    @Test
    void testGetMacronutrientStatistics() {
        // Arrange
        Integer guestUserId = 1;
        Guest mockGuest = new Guest(); // Create a mock Guest instance
        when(guestRepository.findByUserId(guestUserId)).thenReturn(java.util.Optional.of(mockGuest));

        // Act
        List<DietSummaryDto> result = dietService.getMacronutrienseStatisztics(guestUserId);

        // Assert
        assertNotNull(result);
        // Add specific assertions based on your expected results
    }

    @Test
    void testGetCalories() {
        // Arrange
        Integer userId = 1;
        Guest mockGuest = new Guest(); // Create a mock Guest instance
        when(guestRepository.findByUserId(userId)).thenReturn(java.util.Optional.of(mockGuest));
        when(dietRepository.getCaloriesSumByDate(mockGuest.getId())).thenReturn(100.0);
        when(dietRepository.getCaloriesSumForCurrentWeek(mockGuest.getId())).thenReturn(200.0);
        when(dietRepository.getCaloriesSumForCurrentMonth(mockGuest.getId())).thenReturn(300.0);

        // Act
        CaloriesSumDto result = dietService.getCalores(userId);

        // Assert
        assertNotNull(result);
        // Add specific assertions based on your expected results
    }

    @Test
    void testGetMealFrequency() {
        // Arrange
        Integer userId = 1;
        Guest mockGuest = new Guest(); // Create a mock Guest instance
        when(guestRepository.findByUserId(userId)).thenReturn(java.util.Optional.of(mockGuest));

        // Act
        List<MealFrequencyDto> result = dietService.getMealFrequency(userId);

        // Assert
        assertNotNull(result);
        // Add specific assertions based on your expected results
    }

    @Test
    void testGetAllDietByGuestId() {
        // Arrange
        int guestId = 1;
        List<Diet> mockDiets = new ArrayList<>(); // Create a list of mock Diet instances
        when(dietRepository.getAllDietByGuestId(guestId)).thenReturn(mockDiets);

        // Act
        List<CalendarEventDto> result = dietService.getAllDietByGuestId(guestId);

        // Assert
        assertNotNull(result);
        // Add specific assertions based on your expected results
    }
    @Test
    void testSetEated() {
        // Arrange
        int dietId = 1;
        Diet mockDiet = new Diet(); // Create a mock Diet instance
        when(dietRepository.findById(dietId)).thenReturn(java.util.Optional.of(mockDiet));

        // Act
        dietService.setEated(dietId);

        // Assert
        assertTrue(mockDiet.isEated());
        verify(dietRepository, times(1)).save(any(Diet.class));
    }
}
