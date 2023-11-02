package com.example.fitness.exception;

public class ExerciseNotFoundException extends RuntimeException{
    public ExerciseNotFoundException(String msg){
        super(msg);
    }
}
