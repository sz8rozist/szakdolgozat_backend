package com.example.fitness.model.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NutritionDto {
    private Integer protein;
    private Integer calories;
    private Integer carbohydrates;
    private Integer fat;
}
