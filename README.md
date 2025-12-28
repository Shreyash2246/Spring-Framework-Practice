# Spring Framework Practice - Input Validation

This repository documents my learning journey with Spring Boot, specifically focusing on input validation using Spring Boot Starter Validation and custom annotations.

## Table of Contents
- [Input Validation Overview](#input-validation-overview)
- [Built-in Validation Annotations](#built-in-validation-annotations)
- [Custom Annotations](#custom-annotations)
- [Key Learnings](#key-learnings)

---

## Input Validation Overview

Without validation, DTOs (Data Transfer Objects) are passed directly from the controller to the service layer and stored in the database without checking if the data is correct. For example, an email field might contain invalid data like "notanemail" instead of a proper email format.

**Spring Boot Starter Validation** provides runtime annotations that validate data before it reaches the service layer, ensuring data integrity at the controller level.

### Why Validation Matters
- Prevents invalid data from entering the database
- Provides immediate feedback to API consumers
- Reduces service layer complexity
- Maintains data quality and consistency

**📝 Commit:** [Runtime Annotations Implementation](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/9e9722d9bde0749fd8f8825d5059979e184e7047)

---

## Built-in Validation Annotations

Spring Boot provides several built-in validation annotations that can be applied to DTO fields:

### Common Validation Annotations

#### `@NotNull`
Ensures the field is not null.

```java
@NotNull(message = "age cannot be null")
private Integer age;
```

#### `@NotEmpty`
Ensures the field is not null and not empty (for collections and strings).

#### `@NotBlank`
Ensures the string is not null, not empty, and not just whitespace.

```java
@NotBlank(message = "role cannot be blank")
private String role;
```

#### `@Size`
Validates the size of strings, collections, arrays, and maps.

```java
@Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
private String name;
```

#### `@Email`
Validates that the string is a properly formatted email address.

```java
@Email(message = "email should be valid")
private String email;
```

#### `@Min` and `@Max`
Validates that a number is within a specified range.

```java
@Min(value = 18, message = "age should be at least 18")
@Max(value = 65, message = "age should be at most 65")
private Integer age;
```

#### `@DecimalMin` and `@DecimalMax`
Validates decimal/double values with more precision control.

```java
@DecimalMin(value = "0.0", inclusive = false, message = "salary must be greater than 0")
@DecimalMax(value = "100000.0", message = "salary must be less than or equal to 100,000")
@Digits(integer = 6, fraction = 2, message = "salary must be a valid monetary amount")
private Double salary;
```

#### `@Pattern`
Validates that a string matches a specific regular expression.

```java
@Pattern(regexp = "^(ADMIN|USER)$", message = "role must be either ADMIN or USER")
private String role;
```

#### `@PastOrPresent`
Ensures the date is in the past or present, not in the future.

```java
@PastOrPresent(message = "dateOfJoining cannot be in the future")
private LocalDate dateOfJoining;
```

#### `@AssertTrue`
Validates that a boolean field is true.

```java
@AssertTrue(message = "isActive must be true")
private boolean isActive;
```

### Complete Example from EmployeeDTO

```java
package com.springbootwebtutorial.web_demo.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.*;

public class EmployeeDTO {
    
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    private String name;

    @Email(message = "email should be valid")
    private String email;

    @NotNull(message = "age cannot be null")
    @Min(value = 18, message = "age should be at least 18")
    @Max(value = 65, message = "age should be at most 65")
    private Integer age;

    @Digits(integer = 6, fraction = 2, message = "salary must be a valid monetary amount")
    @DecimalMin(value = "0.0", inclusive = false, message = "salary must be greater than 0")
    @DecimalMax(value = "100000.0", message = "salary must be less than or equal to 100,000")
    private Double salary;

    @NotBlank(message = "role cannot be blank")
    @Pattern(regexp = "^(ADMIN|USER)$", message = "role must be either ADMIN or USER")
    private String role;

    @PastOrPresent(message = "dateOfJoining cannot be in the future")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "isActive must be true")
    private boolean isActive;
}
```

---

## Custom Annotations

When built-in annotations don't meet specific business requirements, we can create custom validation annotations. This involves two components:

### 1. Annotation Structure Class

The annotation interface defines the validation metadata.

**📝 Commit:** [Custom Annotation Structure](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/9404ad014e917671e0c08b2dd1409f509ea23aa8)

```java
package com.springbootwebtutorial.web_demo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = EmployeeRoleValidator.class)
public @interface EmployeeRoleValidation {
    String message() default "Invalid role";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
```

**Key Components:**
- `@Retention(RetentionPolicy.RUNTIME)`: Makes the annotation available at runtime
- `@Target`: Specifies where the annotation can be applied (fields, parameters, etc.)
- `@Constraint(validatedBy = ...)`: Links the annotation to its validator class
- `message()`, `groups()`, `payload()`: Required methods for Bean Validation

### 2. Validator Class

The validator class contains the actual validation logic.

**📝 Commit:** [Custom Validation Logic](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/c100d71a70a8f5aee9f75763a37c76812955f87e)

```java
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
```

**How It Works:**
- Implements `ConstraintValidator<A, T>` where A is the annotation type and T is the field type
- The `isValid()` method returns `true` if validation passes, `false` otherwise
- Can contain complex business logic for validation

### Usage in DTO

After creating the custom annotation, apply it to fields just like built-in annotations:

```java
@EmployeeRoleValidation
private String role;
```

This replaces the previous `@Pattern` annotation with a more maintainable and reusable custom validation.

---

## Key Learnings

### 1. Separation of Concerns
Validation annotations keep validation logic separate from business logic. The service layer doesn't need to validate data format - it can focus on business rules.

### 2. Scalability and Maintainability
Adding new fields to DTOs and entities doesn't require changing the entire logic of the service or controller layers. Simply annotate the new fields with appropriate validation constraints.

**Example:**
```java
// Adding a new field is straightforward
@NotBlank(message = "department cannot be blank")
private String department;
```

No changes needed in:
- Controller methods
- Service layer logic
- Database queries (handled by JPA/Hibernate)

### 3. Custom Validation Benefits
- Encapsulates business-specific validation rules
- Reusable across multiple fields and classes
- More readable than complex regex patterns
- Easier to maintain and update validation logic

### 4. Validation Flow
```
Request → Controller (@Valid annotation) → Validation Annotations Check → 
Success: Proceed to Service Layer → Database
Failure: Return validation error messages to client
```

---

## Technologies Used
- **Spring Boot** - Application framework
- **Spring Boot Starter Validation** - Validation support
- **Jakarta Validation API** - Standard validation annotations
- **Hibernate Validator** - Validation implementation

*This README documents my hands-on learning experience with Spring Boot validation. Each concept has been implemented and tested in real code.*
