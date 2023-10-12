package com.example.fitness.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "guest")
@Getter
@Setter
public class Guest extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private int height;

    private String first_name;
    private String last_name;
    private int age;
    @OneToMany(mappedBy = "guest")
    private List<Notification> notifications;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    private Trainer trainer;

    @ManyToMany(mappedBy = "guests")
    private Set<Diet> diets = new HashSet<>();

    @ManyToMany(mappedBy = "guests")
    private Set<Workout> workouts = new HashSet<>();
}
