package com.example.fitness.model.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutRequest {
    private Integer exerciseId;
    private Integer repetitions;
    private Integer sets;
}
