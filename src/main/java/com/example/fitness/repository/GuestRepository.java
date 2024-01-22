package com.example.fitness.repository;

import com.example.fitness.model.Guest;
import com.example.fitness.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {
    Optional<Guest> findByUserId(Integer user_id);

    @Query("SELECT gt FROM Guest g JOIN g.trainer gt WHERE g.id = :guestId")
    Optional<Trainer> findTrainerByGuestId(@Param("guestId") Integer guestId);

    @Query("SELECT g FROM Guest g WHERE g.trainer.id = :trainerId")
    List<Guest> getTrainerGuests(@Param("trainerId") Integer trainerId);
}
