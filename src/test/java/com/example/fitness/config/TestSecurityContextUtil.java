package com.example.fitness.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestSecurityContextUtil {
    @Test
    void setAuthenticationTest() {
        // Create a sample authentication object
        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");

        // Set authentication using the SecurityContextUtil
        SecurityContextUtil.setAuthentication(authentication);

        // Verify that the authentication is set correctly
        assertEquals(authentication, SecurityContextUtil.getAuthentication());
    }

    @Test
    void clearAuthenticationTest() {
        // Set authentication first
        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");
        SecurityContextUtil.setAuthentication(authentication);

        // Clear authentication using the SecurityContextUtil
        SecurityContextUtil.clearAuthentication();

        // Verify that authentication is cleared
        assertNull(SecurityContextUtil.getAuthentication());
    }

}
