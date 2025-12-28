package com.springbootwebtutorial.web_demo.advices;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springbootwebtutorial.web_demo.exceptions.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handlerResourceNotFound(ResourceNotFoundException exception) {
        ApiError apiError = ApiError.builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message(exception.getMessage())
                            .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> MethodArgumentNotValidException(Exception exception) {
        ApiError apiError = ApiError.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .message(exception.getMessage())
                            .build();
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInputValidationErrors(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                                            .getFieldErrors()
                                            .stream()
                                            .map(error -> error.getDefaultMessage())
                                            .collect(Collectors.toList());
        
        ApiError apiError = ApiError.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(errors.toString())
                            .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
