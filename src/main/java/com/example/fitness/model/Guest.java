package com.example.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.sql.In;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "guest")
@Getter
@Setter
public class Guest{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    @Column(name = "height", columnDefinition = "float default null")
    private float height;

    @Column(name = "weight", columnDefinition = "float default null")
    private float weight;

    private String first_name;
    private String last_name;
    @Column(name = "age", columnDefinition = "int default null")
    private Integer age;
    @OneToMany(mappedBy = "guest")
    private List<Notification> notifications;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    private Trainer trainer;

    @OneToMany(mappedBy = "guest")
    Set<DietGuest> dietGuests;

    @OneToMany(mappedBy = "guest")
    Set<WorkoutGuest> workoutGuests;
}
