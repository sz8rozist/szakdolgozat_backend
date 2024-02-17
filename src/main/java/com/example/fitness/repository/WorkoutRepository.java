package com.example.fitness.repository;

import com.example.fitness.model.Diet;
import com.example.fitness.model.Workout;
import com.example.fitness.model.dto.ExerciseRegularityDto;
import com.example.fitness.model.dto.RecentlyUsedExerciseDto;
import com.example.fitness.model.dto.WorkoutDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    @Query("SELECT new com.example.fitness.model.dto.WorkoutDto(w.id, w.exercise, w.date, w.sets, w.repetitions, wg.trainer.id, w.done)  FROM Workout w JOIN w.workoutGuests wg WHERE wg.guest.id = :guestId AND w.date = :date ")
    List<WorkoutDto> findWorkoutsByGuestIdAndWorkoutDate(@Param("guestId") int guestId, @Param("date") LocalDate date);

    //Legtöbbet használt gyakorlatok
    @Query("SELECT new com.example.fitness.model.dto.RecentlyUsedExerciseDto(we.name, SUM(w.repetitions), SUM(w.sets), COUNT(we.id)) FROM Workout w JOIN w.exercise we JOIN w.workoutGuests wg WHERE wg.guest.id = :guestId GROUP BY we.name ORDER BY SUM(w.sets) DESC")
    List<RecentlyUsedExerciseDto> findRecentlyUsedExercise(@Param("guestId") int guestId);

    //Havi edzés rendszeresség
    @Query("SELECT new com.example.fitness.model.dto.ExerciseRegularityDto(MONTHNAME(w.date), COUNT(w.id)) FROM Workout w INNER JOIN w.workoutGuests wg WHERE wg.guest.id = :guestId GROUP BY MONTH(w.date) ORDER BY COUNT(w.id)")
    List<ExerciseRegularityDto> findExerciseRegularity(@Param("guestId") int guestId);
    @Query("SELECT w FROM Workout w JOIN w.workoutGuests wg WHERE wg.guest.id = :guestId GROUP BY w.date")
    List<Workout> getAllWorkoutByGuest(@Param("guestId") Integer guestId);
}
