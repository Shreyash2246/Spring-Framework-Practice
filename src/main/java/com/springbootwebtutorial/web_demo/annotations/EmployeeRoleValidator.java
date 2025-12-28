package com.springbootwebtutorial.web_demo.annotations;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeRoleValidator implements ConstraintValidator<EmployeeRoleValidation, String> {

    @Override
    public boolean isValid(String inputRole, ConstraintValidatorContext context) {
        if(inputRole == null) return false;
        List<String> Roles = List.of("ADMIN", "USER");
        return Roles.contains(inputRole);
    }
}
