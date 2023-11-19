package com.example.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
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
    @Column(name = "eated", columnDefinition = "TINYINT DEFAULT 0")
    private boolean eated;
    @Temporal(TemporalType.DATE)
    private LocalDate date;

    @ManyToOne
    private Food food;

    @OneToMany(mappedBy = "diet")
    @JsonIgnore
    private Set<DietGuest> dietGuests;

}
