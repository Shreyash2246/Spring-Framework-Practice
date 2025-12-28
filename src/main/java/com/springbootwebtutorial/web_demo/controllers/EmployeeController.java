package com.springbootwebtutorial.web_demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.springbootwebtutorial.web_demo.dto.EmployeeDTO;
import com.springbootwebtutorial.web_demo.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootwebtutorial.web_demo.services.employeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    
    private final employeeService employeeService;

    public EmployeeController(employeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    // http://localhost:9090/employees/1
    // status 200 + employeeDTO in body
    // status 404 + empty body
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Long id) {
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    // http://localhost:9090/employees
    // ResponseEntity.ok() means status 200
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) Integer age,
                                                @RequestParam(required = false) String sortby) {
        return ResponseEntity.ok(employeeService.getAllEmployees(age, sortby));
    }

    // http://localhost:9090/employees/create
    // here we used HTTP.CREATED status code which is 201
    @PostMapping("/create")
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){
        EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // http://localhost:9090/employees/{id}
    @PutMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id, employeeDTO));
    }

    // http://localhost:9090/employees/{id}
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long id){
        boolean gotDeleted =  employeeService.deleteEmployeeById(id);
        if (!gotDeleted) {
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(true);
        }
    }

    // http://localhost:9090/employees/{id}
    @PatchMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> patchEmployee(@RequestBody Map<String, Object> updates, 
                                     @PathVariable Long id) {
        EmployeeDTO employeeDTO = employeeService.patchEmployee(id, updates);
        if (employeeDTO == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(employeeDTO);
        }
    }
}
