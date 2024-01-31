package com.example.fitness.model.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecentlyUsedExerciseDto {
    private String exercise;
    private Long sumRepetitions;
    private Long sumSets;
    private Long countExercise;
}
