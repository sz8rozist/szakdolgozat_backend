package com.example.fitness.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDietGuest {
    private DietGuest dietGuest;

    @BeforeEach
    void setUp() {
        dietGuest = new DietGuest();
    }

    @Test
    void setId() {
        dietGuest.setId(1);
        assertEquals(1, dietGuest.getId());
    }

    @Test
    void setGuest() {
        Guest guest = new Guest();
        dietGuest.setGuest(guest);
        assertEquals(guest, dietGuest.getGuest());
    }

    @Test
    void setTrainer() {
        Trainer trainer = new Trainer();
        dietGuest.setTrainer(trainer);
        assertEquals(trainer, dietGuest.getTrainer());
    }

    @Test
    void setDiet() {
        Diet diet = new Diet();
        dietGuest.setDiet(diet);
        assertEquals(diet, dietGuest.getDiet());
    }

    @Test
    void testRelationshipWithGuest() {
        Guest guest = new Guest();
        dietGuest.setGuest(guest);
        Set<DietGuest> dietGuests = new HashSet<>();
        guest.setDietGuests(dietGuests);
        dietGuests.add(dietGuest);
        assertEquals(guest, dietGuest.getGuest());
        assertTrue(guest.getDietGuests().contains(dietGuest));
    }

    @Test
    void testRelationshipWithTrainer() {
        Trainer trainer = new Trainer();
        dietGuest.setTrainer(trainer);
        Set<DietGuest> dietGuests = new HashSet<>();
        trainer.setDietGuests(dietGuests);
        dietGuests.add(dietGuest);
        assertEquals(trainer, dietGuest.getTrainer());
        assertTrue(trainer.getDietGuests().contains(dietGuest));
    }

    @Test
    void testRelationshipWithDiet() {
        Diet diet = new Diet();
        dietGuest.setDiet(diet);
        Set<DietGuest> dietGuests = new HashSet<>();
        dietGuests.add(dietGuest);
        diet.setDietGuests(dietGuests);
        assertEquals(diet, dietGuest.getDiet());
        assertTrue(diet.getDietGuests().contains(dietGuest));
    }
}
