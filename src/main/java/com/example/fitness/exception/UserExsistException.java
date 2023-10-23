package com.example.fitness.exception;

public class UserExsistException extends RuntimeException{
    public UserExsistException(String message) {
        super(message);
    }
}
