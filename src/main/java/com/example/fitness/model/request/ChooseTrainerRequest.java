package com.example.fitness.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ChooseTrainerRequest {
    private Integer trainerId;
    private Integer userId;
}
