package com.example.fitness.exception.handler;

import com.example.fitness.exception.*;
import com.example.fitness.exception.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandlerException {

    private ResponseEntity<Object> buildResponseEntity(Error apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler({InvalidUsernameOrPasswordException.class})
    public ResponseEntity<Object> handleInvalidUsernameOrPassword(InvalidUsernameOrPasswordException exception) {
          return buildResponseEntity(new Error(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }
    @ExceptionHandler({UsernameIsExsistsException.class})
    public ResponseEntity<Object> handleUsernameIsExsist(UsernameIsExsistsException exception){
        return buildResponseEntity(new Error(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler({UserExsistException.class})
    public ResponseEntity<Object> handleUserExsist(UserExsistException exception){
        return buildResponseEntity(new Error(HttpStatus.NOT_FOUND, exception.getMessage()));
    }
    @ExceptionHandler({FileIsEmptyException.class})
    public ResponseEntity<Object> handleFileIsEmpty(FileIsEmptyException exception){
        return buildResponseEntity(new Error(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }
}
