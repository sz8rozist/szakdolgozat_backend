package com.example.fitness.model.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    HttpStatus httpStatus;
    String message;
}
