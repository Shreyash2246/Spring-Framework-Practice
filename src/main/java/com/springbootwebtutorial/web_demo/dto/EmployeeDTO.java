package com.springbootwebtutorial.web_demo.dto;

import java.time.LocalDate;

import com.springbootwebtutorial.web_demo.annotations.EmployeeRoleValidation;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
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

    @Email(message = "email should be valid")
    private String email;

    @NotNull(message = "age cannot be null")
    @Min(value = 18, message = "age should be at least 18")
    @Max(value = 65, message = "age should be at most 65")
    private int age;

    @NotNull(message = "salary cannot be null")
    @Positive(message = "salary must be positive")
    @Digits(integer = 6, fraction = 2, message = "salary must be a valid monetary amount")
    @DecimalMin(value = "0.0", inclusive = false, message = "salary must be greater than 0")
    @DecimalMax(value = "100000.0", message = "salary must be less than or equal to 100,000")
    private Double salary;

    @NotBlank(message = "role cannot be blank")
    @EmployeeRoleValidation
    private String role;

    @PastOrPresent(message = "dateOfJoining cannot be in the future")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "isActive must be true")
    private boolean isActive;
    
}
