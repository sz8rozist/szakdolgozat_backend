package com.example.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "diet_recommedation")
@Entity
@Getter
@Setter
public class DietRecommedation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate date;
    private float calorie;
    private float protein;
    private float carbonhydrate;
    private float fat;
    @ManyToOne
    private Guest guest;
    @ManyToOne
    @JsonIgnore
    private Trainer trainer;
}
