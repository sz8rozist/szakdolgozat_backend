package com.example.fitness.repository;

import com.example.fitness.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.id != :userId")
    List<User> findAllExceptUser(@Param("userId") Integer userId);
}
