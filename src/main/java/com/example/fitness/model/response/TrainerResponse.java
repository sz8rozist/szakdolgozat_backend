package com.example.fitness.model.response;

import com.example.fitness.model.Trainer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TrainerResponse {
    private Trainer trainer;
    private String userProfileImage;

    public TrainerResponse(Trainer trainer, String userProfileImage) {
        this.trainer = trainer;
        this.userProfileImage = userProfileImage;
    }
}
