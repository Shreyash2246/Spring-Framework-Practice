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
    public ResponseEntity<String> handlerResourceNotFound(NoSuchElementException exception) {
        return new ResponseEntity<>("Resource not found: ", HttpStatus.NOT_FOUND);
    }
}
