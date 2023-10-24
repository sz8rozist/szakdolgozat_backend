package com.example.fitness.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "user")
@Getter
@Setter

public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    private String profilePictureName = null;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Guest guest;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Trainer trainer;

    public Optional<Guest> getGuest() {
        return Optional.ofNullable(guest);
    }

    public Optional<Trainer> getTrainer() {
        return Optional.ofNullable(trainer);
    }
}
