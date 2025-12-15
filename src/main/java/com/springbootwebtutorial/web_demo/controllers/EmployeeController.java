package com.springbootwebtutorial.web_demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.springbootwebtutorial.web_demo.dto.EmployeeDTO;
import com.springbootwebtutorial.web_demo.entities.EmployeeEntity;
import java.util.List;
import java.util.Map;

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

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    
    private final employeeService employeeService;

    public EmployeeController(employeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    // http://localhost:9090/employees/1
    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable("id") Long id) {
        return employeeService.getEmployeeById(id);
    }

    // http://localhost:9090/employees
    @GetMapping
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false) Integer age,
                                                @RequestParam(required = false) String sortby) {
        return employeeService.getAllEmployees(age, sortby);
    }
    
    // http://localhost:9090/employees/create
    @PostMapping("/create")
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeEntity inputEmployee){
        return employeeService.createNewEmployee(inputEmployee);
    }

    // http://localhost:9090/employees/{id}
    @PutMapping(path = "/{id}")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long id) {
        return employeeService.getEmployeeById(id, employeeDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
    }

    @PatchMapping(path = "/{id}")
    public EmployeeDTO patchEmployee(@RequestBody Map<String, Object> updates, 
                                     @PathVariable Long id) {
        return employeeService.patchEmployee(id, updates);
    }
}
