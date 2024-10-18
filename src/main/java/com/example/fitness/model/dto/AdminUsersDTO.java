package com.example.fitness.model.dto;

import com.example.fitness.model.Guest;
import com.example.fitness.model.Trainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUsersDTO {
    private List<Trainer> trainers;
    private List<Guest> guests;
}
