package com.springbootwebtutorial.web_demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.springbootwebtutorial.web_demo.dto.EmployeeDTO;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class EmployeeController {

        // Sample endpoint to get an employee by ID
        @GetMapping("/employees/{id}")
        public EmployeeDTO getEmployeeById(@PathVariable Long id) {
            return new EmployeeDTO(id, "John Doe", "john.doe@example.com", 30, LocalDate.of(2020, 1, 15), true);
        }

}
