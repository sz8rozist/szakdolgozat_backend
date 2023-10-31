package com.example.fitness.repository;

import com.example.fitness.model.DietGuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietGuestRepository extends JpaRepository<DietGuest, Integer> {
}
