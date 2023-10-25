package com.example.fitness.repository;

import com.example.fitness.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    @Query("SELECT t, u.profilePictureName FROM Trainer t JOIN t.user u")
    List<Object[]> findTrainersWithProfileImage();
}
