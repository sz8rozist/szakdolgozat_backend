package com.example.fitness.exception;

public class MessageNotFoundException extends RuntimeException{
    public MessageNotFoundException(String msg){
        super(msg);
    }
}
