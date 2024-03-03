package com.example.fitness.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDietRecommedation {
    private DietRecommedation dietRecommedation;

    @BeforeEach
    void setUp() {
        dietRecommedation = new DietRecommedation();
    }

    @Test
    void setId() {
        dietRecommedation.setId(1);
        assertEquals(1, dietRecommedation.getId());
    }

    @Test
    void setDate() {
        LocalDate date = LocalDate.now();
        dietRecommedation.setDate(date);
        assertEquals(date, dietRecommedation.getDate());
    }

    @Test
    void setCalorie() {
        dietRecommedation.setCalorie(2000);
        assertEquals(2000, dietRecommedation.getCalorie());
    }

    @Test
    void setProtein() {
        dietRecommedation.setProtein(50);
        assertEquals(50, dietRecommedation.getProtein());
    }

    @Test
    void setCarbonhydrate() {
        dietRecommedation.setCarbonhydrate(300);
        assertEquals(300, dietRecommedation.getCarbonhydrate());
    }

    @Test
    void setFat() {
        dietRecommedation.setFat(70);
        assertEquals(70, dietRecommedation.getFat());
    }

    @Test
    void setBodyWeight() {
        dietRecommedation.setBodyWeight(70);
        assertEquals(70, dietRecommedation.getBodyWeight());
    }

    @Test
    void setGuest() {
        Guest guest = new Guest();
        dietRecommedation.setGuest(guest);
        assertEquals(guest, dietRecommedation.getGuest());
    }

    @Test
    void setTrainer() {
        Trainer trainer = new Trainer();
        dietRecommedation.setTrainer(trainer);
        assertEquals(trainer, dietRecommedation.getTrainer());
    }

    @Test
    void testRelationshipWithGuest() {
        Guest guest = new Guest();
        dietRecommedation.setGuest(guest);
        Set<DietRecommedation> dietRecommedationSet = new HashSet<>();
        dietRecommedationSet.add(dietRecommedation);
        guest.setDietRecommedations(dietRecommedationSet);
        assertEquals(guest, dietRecommedation.getGuest());
        assertTrue(guest.getDietRecommedations().contains(dietRecommedation));
    }

    @Test
    void testRelationshipWithTrainer() {
        Trainer trainer = new Trainer();
        dietRecommedation.setTrainer(trainer);
        Set<DietRecommedation> dietRecommedationSet = new HashSet<>();
        dietRecommedationSet.add(dietRecommedation);
        trainer.setDietRecommedations(dietRecommedationSet);
        assertEquals(trainer, dietRecommedation.getTrainer());
        assertTrue(trainer.getDietRecommedations().contains(dietRecommedation));
    }
}
