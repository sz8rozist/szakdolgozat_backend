package com.example.fitness.model.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaloriesSumDto {
    private Double sumCaloriesByDay;
    private Double sumCaloriesByWeek;
    private Double sumCaloriesByMonth;
}
