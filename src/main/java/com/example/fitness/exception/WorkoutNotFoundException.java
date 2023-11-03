package com.example.fitness.exception;

public class WorkoutNotFoundException extends RuntimeException{
    public WorkoutNotFoundException(String msg){
        super(msg);
    }
}
