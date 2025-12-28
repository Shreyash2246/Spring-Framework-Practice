package com.springbootwebtutorial.web_demo.advices;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

// to send custom error response
@Data
@Builder
public class ApiError {

    private HttpStatus status;
    private String message;
}
