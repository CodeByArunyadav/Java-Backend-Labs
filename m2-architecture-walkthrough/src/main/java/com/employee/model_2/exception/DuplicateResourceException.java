package com.employee.model_2.exception;

public class DuplicateResourceException extends RuntimeException {
private String message;

public DuplicateResourceException(String message)
{
    super(message);
}
}
