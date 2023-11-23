package com.example.fitness.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TrainerDietNotificationRequestDto {
    private int trainerId;
    private int guestId;
    private int foodId;
    private int dietId;
}
