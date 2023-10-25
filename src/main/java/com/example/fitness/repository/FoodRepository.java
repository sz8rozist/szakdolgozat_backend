package com.example.fitness.repository;

import com.example.fitness.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    @Override
    Page<Food> findAll(Pageable pageable);
}
