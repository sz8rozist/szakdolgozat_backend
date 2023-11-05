package com.example.fitness.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg){
        super(msg);
    }
}
