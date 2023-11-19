package com.example.fitness.model.dto;

import com.example.fitness.model.Diet;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class DietGuestDto {
    private List<DietDto> diet;
    private int calorieSum;
    private int proteinSum;
    private int carbonhydrateSum;
    private int fatSum;
}
