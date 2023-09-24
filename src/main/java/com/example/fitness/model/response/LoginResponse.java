package com.example.fitness.model.response;

import com.example.fitness.model.User;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private User user;
    private String token;
}
