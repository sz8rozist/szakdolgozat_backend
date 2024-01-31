package com.example.fitness.repository;

import com.example.fitness.model.DietGuest;
import com.example.fitness.model.WorkoutGuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkoutGuestRepository extends JpaRepository<WorkoutGuest, Integer> {

    Optional<WorkoutGuest> findWorkoutGuestByWorkoutIdAndGuestId(Integer workoutId, Integer guestId);
    @Query("SELECT COUNT(*) FROM WorkoutGuest wg WHERE wg.trainer.id = :trainerId")
    Integer countTrainerWorkoutPlan(@Param("trainerId") int trainerId);
}
