package com.example.fitness.exception;

public class FoodNotFoundException extends RuntimeException{
    public FoodNotFoundException(String msg){
        super(msg);
    }
}
