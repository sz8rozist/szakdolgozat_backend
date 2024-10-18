package com.example.fitness.repository;

import com.example.fitness.model.Role;
import com.example.fitness.model.RoleEnumType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(RoleEnumType role);
}
