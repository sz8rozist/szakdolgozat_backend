package com.example.fitness.model.response;

import com.example.fitness.model.Diet;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class DietResponse {
    private List<Diet> diet;
    private int calorieSum;
    private int proteinSum;
    private int carbonhydrateSum;
    private int fatSum;
}
