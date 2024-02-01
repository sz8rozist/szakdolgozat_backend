package com.example.fitness.model.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NutritionRequest {
    private int age;
    private int bodyWeight;
    private int chSzazalek;
    private int fatSzazalek;
    private int proteinSzazalek;
    private int guestId;
}
