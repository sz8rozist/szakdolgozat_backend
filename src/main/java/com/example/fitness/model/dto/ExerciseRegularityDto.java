package com.example.fitness.model.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseRegularityDto {
    private String month;
    private Long count;
}
