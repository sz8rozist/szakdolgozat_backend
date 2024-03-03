package com.example.fitness.config;

import com.example.fitness.model.Role;
import com.example.fitness.model.RoleEnumType;
import com.example.fitness.model.User;
import com.example.fitness.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestCustomUserDetailsService {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void loadUserByUsernameTest() {
        // Mock data
        String username = "testUser";
        String password = "testPassword";
        Role role = new Role();
        role.setRole(RoleEnumType.GUEST);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(Collections.singletonList(role));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Execute the method
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // Assertions
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());

        // Verify that the userRepository.findByUsername method was called
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void loadUserByUsernameUserNotFoundTest() {
        // Mock data
        String nonExistingUsername = "nonExistingUser";

        when(userRepository.findByUsername(nonExistingUsername)).thenReturn(Optional.empty());

        // Execute the method and expect an exception
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(nonExistingUsername));

        // Verify that the userRepository.findByUsername method was called
        verify(userRepository, times(1)).findByUsername(nonExistingUsername);
    }
}
