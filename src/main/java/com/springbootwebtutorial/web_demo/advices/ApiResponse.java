package com.springbootwebtutorial.web_demo.advices;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private T data;
    private ApiError error;

    // default constructor for timestamp.
    public ApiResponse() {
        this.timestamp = LocalDateTime.now();   
    }

    //either data or error is set.
    public ApiResponse(T data) {
        this();
        this.data = data;
    }
    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }
}
