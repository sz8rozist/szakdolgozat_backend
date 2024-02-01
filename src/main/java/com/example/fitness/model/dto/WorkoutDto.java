package com.example.fitness.model.dto;

import com.example.fitness.model.Exercise;
import com.example.fitness.model.Trainer;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutDto {
    private Integer workoutId;
    private Exercise exercise;
    private LocalDate date;
    private Integer sets;
    private Integer repetitions;
    private Integer trainer;
    private boolean done;
}
