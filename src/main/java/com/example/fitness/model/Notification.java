package com.example.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "notification")
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   @ManyToOne
   @JsonIgnore
    private Trainer trainer;
   @ManyToOne
   @JsonIgnore
   private Guest guest;
   private String message;
   @Enumerated(EnumType.STRING)
   private NotificationType type;
    @Column(name = "viewed", columnDefinition = "TINYINT DEFAULT 0")
    private boolean viewed;
    @Temporal(value = TemporalType.DATE)
    @ColumnDefault("CURRENT_DATE")
    private LocalDate date;
}
