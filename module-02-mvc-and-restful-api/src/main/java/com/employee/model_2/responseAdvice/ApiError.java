package com.employee.model_2.responseAdvice;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ApiError {
    HttpStatus status;
    String message;
    List<String> subError;
}
