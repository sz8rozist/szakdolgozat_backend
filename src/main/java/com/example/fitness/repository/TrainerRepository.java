package com.example.fitness.repository;

import com.example.fitness.model.Guest;
import com.example.fitness.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    @Query("SELECT t, u.profilePictureName FROM Trainer t JOIN t.user u")
    List<Object[]> findTrainersWithProfileImage();

    Optional<Trainer> findByUserId(Integer user_id);
}
