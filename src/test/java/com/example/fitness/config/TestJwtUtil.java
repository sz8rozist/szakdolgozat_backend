package com.example.fitness.config;

import com.example.fitness.model.User;
import com.example.fitness.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestJwtUtil {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createToken() {
        String username = "testUser";
        User user = new User();
        user.setId(1);
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        String token = jwtUtil.createToken(username);

        assertNotNull(token);
        assertTrue(token.length() > 0);
        verify(userRepository, times(1)).findByUsername(username);
    }
    @Test
    void resolveToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer mockToken");

        String result = jwtUtil.resolveToken(request);

        assertNotNull(result);
        assertEquals("mockToken", result);
        verify(request, times(1)).getHeader("Authorization");
    }

    @Test
    void validateClaims_Valid() {
        Claims claims = Jwts.claims().setExpiration(new Date(System.currentTimeMillis() + 1000));
        boolean result = jwtUtil.validateClaims(claims);
        assertTrue(result);
    }
}
