package com.example.fitness.repository;

import com.example.fitness.model.DietRecommedation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietRecommendationRepository extends JpaRepository<DietRecommedation, Integer> {
}
