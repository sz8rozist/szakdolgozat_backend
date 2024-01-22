package com.example.fitness.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {
    private Integer id;
    private String username;
    private String profilePictureName;
    private String firstName;
    private String lastName;
    private String lastMessage;
}
