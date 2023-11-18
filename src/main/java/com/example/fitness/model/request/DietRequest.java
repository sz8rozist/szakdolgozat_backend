package com.example.fitness.model.request;

import com.example.fitness.model.FoodType;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DietRequest {
    private int quantity;
    private FoodType type;
    private String date;
    private int foodId;
    private int guestId;
    private Integer trainerId;
}
