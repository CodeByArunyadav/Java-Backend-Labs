package com.codebyarunyadav.spring_security.exception;

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
