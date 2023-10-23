package com.example.fitness.model.request;

import jakarta.validation.constraints.*;
import lombok.*;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotEmpty(message = "Vezetéknév megadása kötelező.")
    private String firstName;
    @NotEmpty(message = "Keresztnév megadása kötelező.")
    private String lastName;
    @NotEmpty(message = "Email cím megadása kötelező.")
    @Email(message = "Hibás email formátum.")
    private String email;
    @NotEmpty(message = "Felhasználónév megadása kötelező.")
    private String username;
    @NotEmpty(message = "Jelszó megadása kötelező.")
    @Size(min = 8, message = "A jelszó minimum 8 karakter hosszú kell legyen.")
    private String password;
    @NotEmpty(message = "A típus megadása kötelező.")
    private String type;
    @NotEmpty(message = "Jogosultság megadása kötelező.")
    @Pattern(regexp = "^(GUEST|TRAINER|ADMIN)$", message = "Érvénytelen jogosultság. Csak GUEST vagy TRAINER megengedett.")
    private String role;
}
