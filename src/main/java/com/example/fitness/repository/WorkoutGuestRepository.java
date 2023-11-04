package com.example.fitness.repository;

import com.example.fitness.model.DietGuest;
import com.example.fitness.model.WorkoutGuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkoutGuestRepository extends JpaRepository<WorkoutGuest, Integer> {

    Optional<WorkoutGuest> findWorkoutGuestByWorkoutIdAndGuestId(Integer workoutId, Integer guestId);
}
