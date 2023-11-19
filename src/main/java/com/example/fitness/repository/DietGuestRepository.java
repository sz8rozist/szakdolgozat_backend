package com.example.fitness.repository;

import com.example.fitness.model.DietGuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DietGuestRepository extends JpaRepository<DietGuest, Integer> {
    Optional<DietGuest> findDietGuestByDietIdAndGuestId(Integer dietId, Integer guestId);
    List<DietGuest> findDietGuestByGuestId(Integer guestId);
}
