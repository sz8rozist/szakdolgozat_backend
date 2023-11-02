package com.example.fitness.model.request;

import com.example.fitness.model.Food;
import com.example.fitness.model.FoodType;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DietUpdateRequest {
    private int quantity;
    private FoodType type;
    private LocalDate date;
    private Integer foodId;
}
