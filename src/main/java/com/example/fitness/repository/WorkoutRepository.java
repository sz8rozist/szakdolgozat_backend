package com.example.fitness.repository;

import com.example.fitness.model.Diet;
import com.example.fitness.model.Workout;
import com.example.fitness.model.dto.WorkoutDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    @Query("SELECT new com.example.fitness.model.dto.WorkoutDto(w.id, w.exercise, w.date, w.sets, w.repetitions, wg.trainer, w.done)  FROM Workout w JOIN w.workoutGuests wg WHERE wg.guest.id = :guestId AND w.date = :date")
    List<WorkoutDto> findWorkoutsByGuestIdAndWorkoutDate(@Param("guestId") Integer guestId, @Param("date") LocalDate date);
}
