package com.example.fitness.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "food")
@Getter
@Setter
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private int calorie;
    private float protein;
    private float carbonhydrate;
    private float fat;

    @OneToMany(mappedBy = "food")
    private List<Diet> diets;
}
