package com.springbootwebtutorial.web_demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.springbootwebtutorial.web_demo.dto.EmployeeDTO;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
// parent path for all methods in this controller
@RequestMapping(path = "/employees")
public class EmployeeController {

        // GET http://localhost:8080/employees/{employeeId}
        // name parameter in @PathVariable should match the placeholder in the URL
        @GetMapping(path = "/{employeeId}")
        public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id) {
            return new EmployeeDTO(id, "John Doe", "john.doe@example.com", 30, LocalDate.of(2020, 1, 15), true);
        }

        // GET http://localhost:8080/employees?EmployeeAge=25&sortby=name
        // used name attribute in @RequestParam to map request param EmployeeAge to method param age
        @GetMapping
        public String getMethodName(@RequestParam(required = false, name = "EmployeeAge") Integer age, 
                                    @RequestParam(required = false) String sortby) {
            return "The age is " + age+" and sortby is "+sortby;
        }
        
}
