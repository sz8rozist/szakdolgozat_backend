package com.example.fitness.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDiet {
    private Diet diet;

    @BeforeEach
    void setUp() {
        diet = new Diet();
    }

    @Test
    void setId() {
        diet.setId(1);
        assertEquals(1, diet.getId());
    }

    @Test
    void setQuantity() {
        diet.setQuantity(5);
        assertEquals(5, diet.getQuantity());
    }

    @Test
    void setType() {
        diet.setType(FoodType.BREAKFAST);
        assertEquals(FoodType.BREAKFAST, diet.getType());
    }

    @Test
    void setEated() {
        diet.setEated(true);
        assertTrue(diet.isEated());
    }

    @Test
    void setDate() {
        LocalDate date = LocalDate.now();
        diet.setDate(date);
        assertEquals(date, diet.getDate());
    }

    @Test
    void setFood() {
        Food food = new Food();
        diet.setFood(food);
        assertEquals(food, diet.getFood());
    }

    @Test
    void setDietGuests() {
        Set<DietGuest> dietGuests = new HashSet<>();
        diet.setDietGuests(dietGuests);
        assertEquals(dietGuests, diet.getDietGuests());
    }
}
