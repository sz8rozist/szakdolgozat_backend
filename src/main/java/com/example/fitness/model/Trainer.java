package com.example.fitness.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "trainer")
@Setter
@Getter
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String type;

    @OneToMany(mappedBy = "trainer")
    private List<Notification> notifications;


    @OneToMany(mappedBy = "trainer",  fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Guest> guests;

    @OneToMany(mappedBy = "trainer")
    Set<DietGuest> dietGuests;

    @OneToMany(mappedBy = "trainer")
    Set<WorkoutGuest> workoutGuests;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
}
