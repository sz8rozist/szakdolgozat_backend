package com.example.fitness.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@Entity
@Table(name = "food")
@Getter
@Setter
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Az étel nevének megadása kötelező.")
    private String name;
    @NotNull(message = "Az étel kalória tartalmának megadása kötelező.")
    private int calorie;
    @NotNull(message = "Az étel fehérje tartalmának megadása kötelező.")
    private float protein;
    @NotNull(message = "Az étel szénhidrát tartalmának megadása kötelező.")
    private float carbonhydrate;
    @NotNull(message = "Az étel zsír tartalmának megadása kötelező.")
    private float fat;

    @OneToMany(mappedBy = "food")
    private List<Diet> diets;
}
