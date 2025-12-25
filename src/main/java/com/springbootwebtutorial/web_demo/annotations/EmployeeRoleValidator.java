package com.springbootwebtutorial.web_demo.annotations;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeRoleValidator implements ConstraintValidator<EmployeeRoleValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<String> Roles = List.of("ADMIN", "USER");
        return Roles.contains(value);
    }
}
