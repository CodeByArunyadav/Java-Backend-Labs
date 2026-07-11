package com.employee.model_2.exception;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message)
    {
        super(message);
    }
}
