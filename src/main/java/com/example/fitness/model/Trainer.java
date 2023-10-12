package com.example.fitness.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "trainer")
@Setter
@Getter
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String type;

    @OneToMany(mappedBy = "trainer")
    private List<Notification> notifications;

}
