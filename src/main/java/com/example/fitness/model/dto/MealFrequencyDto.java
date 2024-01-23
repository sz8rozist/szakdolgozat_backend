package com.example.fitness.model.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MealFrequencyDto {
    private String mealName;
    private Long count;
}
