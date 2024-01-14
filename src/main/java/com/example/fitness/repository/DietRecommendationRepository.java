package com.example.fitness.repository;

import com.example.fitness.model.DietRecommedation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DietRecommendationRepository extends JpaRepository<DietRecommedation, Integer> {
    Optional<DietRecommedation> findByGuest_IdAndDate(int guest_id, LocalDate date);
}
