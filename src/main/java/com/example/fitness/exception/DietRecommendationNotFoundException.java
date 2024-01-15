package com.example.fitness.exception;

public class DietRecommendationNotFoundException extends RuntimeException{
    public DietRecommendationNotFoundException(String msg){
        super(msg);
    }
}
