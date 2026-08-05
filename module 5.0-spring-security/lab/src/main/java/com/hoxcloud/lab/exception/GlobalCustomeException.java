package com.hoxcloud.lab.exception;


import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalCustomeException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException exception) {

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiError);

    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError>authenticationException(AuthenticationException exception)
    {
     ApiError apiError= ApiError.builder()
             .status(HttpStatus.BAD_REQUEST)
             .message(exception.getMessage())
             .build();
     return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> jwtExceptionHandler(JwtException exception)
    {
        ApiError apiError=ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiError);

    }

}
