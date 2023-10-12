package com.example.fitness.model.request;

import com.example.fitness.model.Role;
import jakarta.validation.constraints.*;
import lombok.*;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotEmpty(message = "Vezetékné megadása kötelező.")
    private String veznev;
    @NotEmpty(message = "Keresztnév megadása kötelező.")
    private String kernev;
    @NotEmpty(message = "Email cím megadása kötelező.")
    @Email(message = "Hibás email formátum.")
    private String email;
    @NotEmpty(message = "Felhasználónév megadása kötelező.")
    private String username;
    @NotEmpty(message = "Jelszó megadása kötelező.")
    @Size(min = 8, message = "A jelszó minimum 8 karakter hosszú kell legyen.")
    private String password;
    @NotEmpty(message = "Jogosultság megadása kötelező.")
    @Pattern(regexp = "^(GUEST|TRAINER)$", message = "Érvénytelen jogosultság. Csak GUEST vagy TRAINER megengedett.")
    private String role;
}
