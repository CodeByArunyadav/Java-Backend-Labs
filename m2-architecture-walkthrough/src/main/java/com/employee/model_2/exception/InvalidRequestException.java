package com.employee.model_2.exception;

public class InvalidRequestException extends RuntimeException {
    private String message;

    public InvalidRequestException(String message)
    {
        super(message);
    }
}
