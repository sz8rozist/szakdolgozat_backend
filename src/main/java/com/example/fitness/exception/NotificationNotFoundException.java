package com.example.fitness.exception;

public class NotificationNotFoundException extends RuntimeException{
    public NotificationNotFoundException(String msg){
        super(msg);
    }
}
