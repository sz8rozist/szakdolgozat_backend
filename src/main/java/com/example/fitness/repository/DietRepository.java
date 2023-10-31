package com.example.fitness.repository;

import com.example.fitness.model.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DietRepository extends JpaRepository<Diet, Integer> {
    @Query("SELECT DISTINCT d.date FROM Diet d JOIN d.dietGuests dg WHERE dg.guest.id = :guestId")
    List<LocalDate> findDistinctDatesByGuestId(@Param("guestId") Integer guestId);
}
