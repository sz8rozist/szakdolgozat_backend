package com.example.fitness.model.response;

import lombok.*;
import org.springframework.http.HttpStatus;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    HttpStatus httpStatus;
    String message;
}
