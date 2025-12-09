package com.springbootwebtutorial.web_demo.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

// This is a REST controller that handles HTTP GET requests
@RestController
public class EmployeeController {

        // Example URL: http://localhost:9090/getMethod
        @GetMapping(path = "/getMethod")
        public String getSecretMessage() {
            return "secret message : uhd123jsdh!@#";
        }
        
}
