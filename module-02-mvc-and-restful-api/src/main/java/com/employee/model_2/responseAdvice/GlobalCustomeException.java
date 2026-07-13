package com.employee.model_2.responseAdvice;

import com.employee.model_2.exception.DuplicateResourceException;
import com.employee.model_2.exception.InvalidRequestException;
import com.employee.model_2.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalCustomeException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException exception) {

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
        return buildApiErrorResponseEntity(apiError);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleApiValidationException(MethodArgumentNotValidException exception) {
        List<String> apiError = exception
                .getBindingResult()
                .getAllErrors()
                .stream().map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());
        ApiError errorAPI = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input validation failed")
                .subError(apiError).build();
        return buildApiErrorResponseEntity(errorAPI);
    }


    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateException(DuplicateResourceException exception) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage()).build();

        return buildApiErrorResponseEntity(apiError);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidRequestException(InvalidRequestException exception) {
        ApiError apiError = ApiError.builder().status(HttpStatus.BAD_REQUEST).message(exception.getMessage()).build();
        return buildApiErrorResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildApiErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse(apiError), apiError.getStatus());
    }

}
