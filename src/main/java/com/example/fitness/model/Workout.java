package com.example.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name="exercise_id")
    private Exercise exercise;
    @Column(name = "done", columnDefinition = "TINYINT DEFAULT 0")
    private boolean done;
    @OneToMany(mappedBy = "workout")
    @JsonIgnore
    Set<WorkoutGuest> workoutGuests;
}
