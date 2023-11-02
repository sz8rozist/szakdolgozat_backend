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
    @JoinColumn(name="exercise_id")
    private Exercise exercise;

    @OneToMany(mappedBy = "workout")
    Set<WorkoutGuest> workoutGuests;
}
