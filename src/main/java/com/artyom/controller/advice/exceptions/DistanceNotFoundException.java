package com.artyom.controller.advice.exceptions;

public class DistanceNotFoundException extends RuntimeException{
    public DistanceNotFoundException(String message) {
        super(message);
    }
}
