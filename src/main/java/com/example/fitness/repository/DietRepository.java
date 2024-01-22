package com.example.fitness.repository;

import com.example.fitness.model.Diet;
import com.example.fitness.model.dto.DietSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DietRepository extends JpaRepository<Diet, Integer> {
    @Query("SELECT d FROM Diet d JOIN d.dietGuests dg WHERE dg.guest.id = :guestId AND d.date = :dietDate")
    List<Diet> findDietsByGuestIdAndDietDate(@Param("guestId") Integer guestId, @Param("dietDate") LocalDate dietDate);
    //Az aktuális havi makronutirenseinek diagramja napokra bontva.
    @Query("SELECT NEW com.example.fitness.model.dto.DietSummaryDto(" +
            "SUM(d.food.protein), " +
            "SUM(d.food.carbonhydrate), " +
            "SUM(d.food.calorie), " +
            "SUM(d.food.fat), " +
            "d.date, " +
            "dg.guest.id, d.type) " +
            "FROM Diet d " +
            "INNER JOIN DietGuest dg ON d.id = dg.diet.id " +
            "WHERE dg.guest.id = :guestId AND DAY(d.date) = DAY(CURRENT_DATE) " +
            "GROUP BY d.date, d.type")
    List<DietSummaryDto> getDietSummary(Integer guestId);

    @Query("SELECT SUM(food.calorie) FROM Diet diet " +
            "INNER JOIN Food food ON diet.food.id = food.id " +
            "INNER JOIN DietGuest dietGuest ON diet.id = dietGuest.diet.id " +
            "WHERE diet.date = CURRENT_DATE AND dietGuest.guest.id = :guestId")
    Double getCaloriesSumByDate(Integer guestId);

    @Query("SELECT SUM(food.calorie) FROM Diet diet " +
            "INNER JOIN Food food ON diet.food.id = food.id " +
            "INNER JOIN DietGuest dietGuest ON diet.id = dietGuest.diet.id " +
            "WHERE WEEK(diet.date) = WEEK(CURRENT_DATE) AND dietGuest.guest.id = :guestId")
    Double getCaloriesSumForCurrentWeek(Integer guestId);

    @Query("SELECT SUM(food.calorie) FROM Diet diet " +
            "INNER JOIN Food food ON diet.food.id = food.id " +
            "INNER JOIN DietGuest dietGuest ON diet.id = dietGuest.diet.id " +
            "WHERE MONTH(diet.date) = MONTH(CURRENT_DATE) AND dietGuest.guest.id = :guestId")
    Double getCaloriesSumForCurrentMonth(Integer guestId);
}
