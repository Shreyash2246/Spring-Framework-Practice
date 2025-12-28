package com.springbootwebtutorial.web_demo.advices;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // employee not found by id
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handlerResourceNotFound(NoSuchElementException exception) {
        ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND).message("Resource not found").build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
