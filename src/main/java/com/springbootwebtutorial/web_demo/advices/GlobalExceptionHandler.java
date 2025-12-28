package com.springbootwebtutorial.web_demo.advices;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springbootwebtutorial.web_demo.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // employee not found by id
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handlerResourceNotFound(ResourceNotFoundException exception) {
        ApiError apiError = ApiError.builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message(exception.getMessage())
                            .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
