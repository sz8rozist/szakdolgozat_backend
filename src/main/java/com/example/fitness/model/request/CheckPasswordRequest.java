package com.example.fitness.model.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckPasswordRequest {
    private String password;
}
