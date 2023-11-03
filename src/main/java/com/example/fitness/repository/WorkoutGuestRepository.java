package com.example.fitness.repository;

import com.example.fitness.model.WorkoutGuest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutGuestRepository extends JpaRepository<WorkoutGuest, Integer> {
}
