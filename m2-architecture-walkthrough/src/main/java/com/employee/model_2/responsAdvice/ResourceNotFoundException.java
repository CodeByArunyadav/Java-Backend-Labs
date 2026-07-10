package com.employee.model_2.responsAdvice;

public class ResourceNotFoundException extends RuntimeException {
    private  final String status;

    public ResourceNotFoundException(String status,String message)
    {
        super(message);
        this.status=status;
     }
}
