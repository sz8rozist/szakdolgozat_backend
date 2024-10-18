package com.example.fitness.config;

import com.example.fitness.model.Role;
import com.example.fitness.model.RoleEnumType;
import com.example.fitness.model.User;
import com.example.fitness.repository.RoleRepository;
import com.example.fitness.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DefaultAdminUserInitializer {
        @Bean
        public CommandLineRunner createDefaultAdmin(UserRepository userRepository,
                                                    RoleRepository roleRepository,
                                                    PasswordEncoder passwordEncoder) {
            return args -> {
                // Ellenőrizd, hogy létezik-e már az admin felhasználó
                if (userRepository.findByUsername("admin").isEmpty()) {
                    // Alapértelmezett admin felhasználó létrehozása
                    User adminUser = new User();
                    adminUser.setUsername("admin");
                    adminUser.setPassword(passwordEncoder.encode("admin"));
                    adminUser.setOnline(false); // offline by default

                    // Admin szerep hozzáadása
                    Role adminRole = new Role();
                    adminRole.setRole(RoleEnumType.ADMIN);
                    adminRole.setUser(adminUser);

                    // Kapcsolat létrehozása a felhasználó és a szerepek között
                    adminUser.setRoles(List.of(adminRole));

                    // Felhasználó mentése
                    userRepository.save(adminUser);
                    log.info("Default admin felhasználó létrehozva.");
                } else {
                    log.info("Admin felhasználó már létezik.");
                }
            };
        }
}
