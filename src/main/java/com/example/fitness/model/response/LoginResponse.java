package com.example.fitness.model.response;

import com.example.fitness.model.User;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private UserDetails userDetails;
    private String token;
}
