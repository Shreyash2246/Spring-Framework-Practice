package com.springbootwebtutorial.web_demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.springbootwebtutorial.web_demo.dto.EmployeeDTO;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

        // Sample endpoint to get an employee by ID
        @GetMapping(path = "/{id}")
        public EmployeeDTO getEmployeeById(@PathVariable Long id) {
            return new EmployeeDTO(id, "John Doe", "john.doe@example.com", 30, LocalDate.of(2020, 1, 15), true);
        }

        // Sample endpoint to demonstrate query parameters
        // path: /employees?age=25&sortby=name
        @GetMapping
        public String getMethodName(@RequestParam(required = false) Integer age, 
                                    @RequestParam(required = false) String sortby) {
            return "The age is " + age+" and sortby is "+sortby;
        }
        
}
