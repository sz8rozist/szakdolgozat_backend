package com.example.fitness.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    @NotEmpty(message = "Vezetéknév megadása kötelező.")
    private String firstName;
    @NotEmpty(message = "Keresztnév megadása kötelező.")
    private String lastName;
    @NotEmpty(message = "Email cím megadása kötelező.")
    @Email(message = "Hibás email formátum.")
    private String email;
    private Integer age;
    private float weight;
    private float height;
    private String type;
}
