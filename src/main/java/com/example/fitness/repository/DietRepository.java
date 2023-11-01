package com.example.fitness.repository;

import com.example.fitness.model.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DietRepository extends JpaRepository<Diet, Integer> {
    @Query("SELECT d FROM Diet d JOIN d.dietGuests dg WHERE dg.guest.id = :guestId AND d.date = :dietDate")
    List<Diet> findDietsByGuestIdAndDietDate(@Param("guestId") Integer guestId, @Param("dietDate") LocalDate dietDate);
}
