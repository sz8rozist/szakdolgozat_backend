package com.example.fitness.model.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietSummary {
    private Double totalProtein;
    private Double totalCarbonhydrate;
    private Double totalCalorie;
    private Double totalFat;
    private LocalDate date;
    private Integer guestId;

}
