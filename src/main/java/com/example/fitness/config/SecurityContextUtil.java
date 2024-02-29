package com.example.fitness.config;

import lombok.Getter;
import org.springframework.security.core.Authentication;

public class SecurityContextUtil {

    @Getter
    private static Authentication authentication;

    public static void setAuthentication(Authentication authentication) {
        SecurityContextUtil.authentication = authentication;
    }

    public static void clearAuthentication() {
        authentication = null;
    }
}
