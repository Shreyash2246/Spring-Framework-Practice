package com.springbootwebtutorial.web_demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.springbootwebtutorial.web_demo.entities.EmployeeEntity;
import com.springbootwebtutorial.web_demo.repositories.EmployeeRepository;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

// reportory injection in controller
// not using service layer for now also not recommended for production level code
@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    // dependency injection via constructor
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    // http://localhost:9090/employees/1
    @GetMapping("/{id}")
    public EmployeeEntity getEmployeeById(@PathVariable("id") Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // http://localhost:9090/employees
    @GetMapping
    public List<EmployeeEntity> getAllEmployees(@RequestParam(required = false) Integer age,
                                                @RequestParam(required = false) String sortby) {
        return employeeRepository.findAll();
    }
    
    // http://localhost:9090/employees/create
    @PostMapping("/create")
    public EmployeeEntity createNewEmployee(@RequestBody EmployeeEntity inputEmployee){
        return employeeRepository.save(inputEmployee);
    }

    // http://localhost:9090/employees/update
    @PutMapping("/update")
    public String updaEmployee() {
        return "Update Employee called";
    }

    // raw json data for testing
    /*
    {
        "name": "John Doe",
        "email": "john.doe@example.com",
        "age": 30,
        "dateOfJoining": "2023-10-01",
        "isActive": true
    }
    */
}
