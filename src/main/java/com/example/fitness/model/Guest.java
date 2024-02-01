package com.example.fitness.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "gender", columnDefinition = "TINYINT DEFAULT 0")
    //0 - nő, 1 - férfi
    private boolean gender;

    @ManyToOne
    private Trainer trainer;

    @OneToMany(mappedBy = "guest")
    @JsonIgnore
    Set<DietGuest> dietGuests;

    @OneToMany(mappedBy = "guest")
    @JsonIgnore
    Set<WorkoutGuest> workoutGuests;
    @OneToMany(mappedBy = "guest")
    Set<DietRecommedation> dietRecommedations;
}
