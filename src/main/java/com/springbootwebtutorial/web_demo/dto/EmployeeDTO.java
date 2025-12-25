package com.springbootwebtutorial.web_demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    
    private Long id;
    @NotBlank(message = "name cannot be blank")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    private String name;
    private String email;
    private int age;
    private LocalDate dateOfJoining;
    private boolean isActive;
    
}
