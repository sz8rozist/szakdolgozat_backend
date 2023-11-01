package com.example.fitness.model.response;

import com.example.fitness.model.Guest;
import com.example.fitness.model.Trainer;
import com.example.fitness.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserResponse {
    private User user;
    private Trainer trainer;
    private Guest guest;
}
