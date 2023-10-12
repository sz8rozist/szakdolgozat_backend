package com.example.fitness.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "trainer")
@Setter
@Getter
public class Trainer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String type;

    @OneToMany(mappedBy = "trainer")
    private List<Notification> notifications;


    @OneToMany(mappedBy = "trainer")
    private List<Guest> guests;

    @ManyToMany(mappedBy = "trainers")
    private Set<Diet> diets = new HashSet<>();

    @ManyToMany(mappedBy = "trainers")
    private Set<Workout> workouts = new HashSet<>();
}
