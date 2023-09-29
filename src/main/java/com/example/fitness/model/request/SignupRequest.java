package com.example.fitness.model.request;

import com.example.fitness.model.Roles;
import lombok.*;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private Roles role;
}
