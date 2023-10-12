package com.example.fitness.repository;

import com.example.fitness.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository<T extends User> extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
