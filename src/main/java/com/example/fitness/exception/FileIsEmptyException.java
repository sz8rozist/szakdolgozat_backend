package com.example.fitness.exception;

public class FileIsEmptyException extends RuntimeException{
    public FileIsEmptyException(String message) {
        super(message);
    }
}
