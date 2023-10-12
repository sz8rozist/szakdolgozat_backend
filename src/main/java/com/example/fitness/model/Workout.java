package com.example.fitness.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "workout")
@Getter
@Setter
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int sets;
    private int repetitions;
    @Temporal(TemporalType.DATE)
    private int date;

    @ManyToOne
    private Exercise exercise;

    @ManyToMany
    @JoinTable(name = "workout_guest",
            joinColumns = @JoinColumn(name = "workout_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id", nullable = true))
    private Set<Guest> guests = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "workout_guest",
            joinColumns = @JoinColumn(name = "workout_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id"))
    private Set<Trainer> trainers = new HashSet<>();
}
