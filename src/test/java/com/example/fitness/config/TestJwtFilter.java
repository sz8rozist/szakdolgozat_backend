package com.example.fitness.config;

import com.example.fitness.model.User;
import com.example.fitness.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;

public class TestJwtFilter {
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JwtFilter jwtFilter;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(jwtFilter).build();
    }

    @Test
    void doFilterInternal_FailedAuthentication() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        when(jwtUtil.resolveToken(request)).thenReturn("mockToken");
        when(jwtUtil.resolveClaims(request)).thenReturn(mock(Claims.class));
        when(jwtUtil.validateClaims(any())).thenReturn(false);

        jwtFilter.doFilterInternal(request, response, filterChain);

    }

    @Test
    void doFilterInternal_ExceptionHandled() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        when(jwtUtil.resolveToken(request)).thenThrow(new RuntimeException("Mock Exception"));

        jwtFilter.doFilterInternal(request, response, filterChain);
    }
}
