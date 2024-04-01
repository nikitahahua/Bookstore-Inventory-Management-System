package com.example.testtask.exceptions;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String exc) {
        super(exc);
    }
}
