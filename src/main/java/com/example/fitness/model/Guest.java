package com.example.fitness.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "guest")
@Getter
@Setter
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private int height;

    private String first_name;
    private String last_name;
    private int age;
    @OneToMany(mappedBy = "guest")
    private List<Notification> notifications;
}
