package com.example.fitness.serviceTest;

import com.example.fitness.exception.FoodNotFoundException;
import com.example.fitness.model.Food;
import com.example.fitness.repository.FoodRepository;
import com.example.fitness.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestFoodService {
    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private FoodService foodService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFood() {
        // Arrange
        int offSet = 0;
        int pageSize = 10;
        List<Food> foods = Arrays.asList(new Food(), new Food());
        Page<Food> foodPage = new PageImpl<>(foods);

        when(foodRepository.findAll(PageRequest.of(offSet, pageSize))).thenReturn(foodPage);

        // Act
        Page<Food> result = foodService.getAllFood(offSet, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(foods, result.getContent());
        verify(foodRepository, times(1)).findAll(PageRequest.of(offSet, pageSize));
    }

    @Test
    public void testSaveFood() {
        // Arrange
        Food foodToSave = new Food();
        when(foodRepository.save(foodToSave)).thenReturn(foodToSave);

        // Act
        Food result = foodService.saveFood(foodToSave);

        // Assert
        assertNotNull(result);
        assertEquals(foodToSave, result);
        verify(foodRepository, times(1)).save(foodToSave);
    }

    @Test
    public void testGetAllFoodWithoutPagination() {
        // Arrange
        List<Food> foods = Arrays.asList(new Food(), new Food());

        when(foodRepository.findAll()).thenReturn(foods);

        // Act
        List<Food> result = foodService.getAllFoodWithoutPagination();

        // Assert
        assertNotNull(result);
        assertEquals(foods, result);
        verify(foodRepository, times(1)).findAll();
    }

    @Test
    public void testGetFoodById() {
        // Arrange
        Integer foodId = 1;
        Food food = new Food();
        when(foodRepository.findById(foodId)).thenReturn(Optional.of(food));

        // Act
        Food result = foodService.getFoodById(foodId);

        // Assert
        assertNotNull(result);
        assertEquals(food, result);
        verify(foodRepository, times(1)).findById(foodId);
    }

    @Test
    public void testGetFoodByIdNotFound() {
        // Arrange
        Integer foodId = 1;
        when(foodRepository.findById(foodId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(FoodNotFoundException.class, () -> foodService.getFoodById(foodId));
    }
}
