package com.example.fitness.repository;

import com.example.fitness.model.DietRecommedation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DietRecommendationRepository extends JpaRepository<DietRecommedation, Integer> {
    Optional<DietRecommedation> findByGuest_IdAndDate(int guest_id, LocalDate date);

    @Query("SELECT dr FROM DietRecommedation dr WHERE dr.guest.id = :guestId AND dr.trainer.id = :trainerId")
    List<DietRecommedation> getAllRecommendation(@Param("guestId") int guestId, @Param("trainerId") int trainerId);
}
