package com.example.fitness.model.dto;

import com.example.fitness.model.FoodType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DietDto {
    private int dietId;
    private FoodType foodType;
    private int quantity;
    private boolean eated;
    private float calorie;
    private float carbonhydrate;
    private float fat;
    private float protein;
    private String name;
    private int foodId;
    private int trainerId;
}
