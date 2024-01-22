package com.example.fitness.model.dto;

import com.example.fitness.model.FoodType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietSummaryDto {
    private Double totalProtein;
    private Double totalCarbonhydrate;
    private Double totalCalorie;
    private Double totalFat;
    private String day;
    private Integer guestId;
    private FoodType foodType;

}
