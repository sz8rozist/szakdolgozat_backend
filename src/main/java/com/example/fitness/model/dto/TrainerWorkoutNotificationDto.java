package com.example.fitness.model.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerWorkoutNotificationDto {
    private int trainerId;
    private int guestId;
    private int exerciseId;
    private int workoutId;
}
