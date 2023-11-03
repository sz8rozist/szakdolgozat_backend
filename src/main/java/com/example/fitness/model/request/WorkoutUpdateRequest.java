package com.example.fitness.model.request;

import com.example.fitness.model.FoodType;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutUpdateRequest {
    private Integer repetitions;
    private Integer sets;
    private LocalDate date;
    private Integer exerciseId;
}
