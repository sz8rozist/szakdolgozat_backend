package com.example.fitness.repository;

import com.example.fitness.model.Exercise;
import com.example.fitness.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    @Override
    Page<Exercise> findAll(Pageable pageable);
}
