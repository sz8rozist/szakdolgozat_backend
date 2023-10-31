package com.example.fitness.exception;

public class GuestNotFoundException extends RuntimeException{
    public GuestNotFoundException(String message){
        super(message);
    }
}
