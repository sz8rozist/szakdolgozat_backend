package com.example.fitness.exception.handler;

import com.example.fitness.exception.Error;
import com.example.fitness.exception.InvalidUsernameOrPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {

    private ResponseEntity<Object> buildResponseEntity(Error apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler({InvalidUsernameOrPasswordException.class})
    public ResponseEntity<Object> handleInvalidUsernameOrPassword(InvalidUsernameOrPasswordException exception) {
          return buildResponseEntity(new Error(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
    }
}
