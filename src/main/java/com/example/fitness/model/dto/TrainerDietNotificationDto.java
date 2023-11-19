package com.example.fitness.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TrainerDietNotificationDto {
    private int trainerId;
    private String message;
    private int guestId;
}
