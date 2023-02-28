package com.artyom.controller.advice.exceptions;

public class UnknownOperationException extends RuntimeException{
    public UnknownOperationException(String message) {
        super(message);
    }
}
