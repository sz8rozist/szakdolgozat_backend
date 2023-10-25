package com.example.fitness.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "diet")
@Getter
@Setter
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private FoodType type;

    @Temporal(TemporalType.DATE)
    private String date;

    @ManyToOne
    private Food food;

    @ManyToMany
    @JoinTable(name = "diet_guest",
            joinColumns = @JoinColumn(name = "diet_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id"))
    private Set<Guest> guests = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "diet_guest",
            joinColumns = @JoinColumn(name = "diet_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id"))
    private Set<Trainer> trainers = new HashSet<>();
}
