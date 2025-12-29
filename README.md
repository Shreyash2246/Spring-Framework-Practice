# Exception Handling in Spring Boot

## Overview
Exception handling is a crucial aspect of building robust Spring Boot applications. It allows us to communicate errors to users effectively, such as when an ID is not present in the database. Proper exception handling ensures that our application provides clear, user-friendly error responses instead of crashing.

## Why Exception Handling is Important

- **Prevents Application Crashes**: Handles errors like division by zero gracefully without stopping the application
- **User-Friendly Error Responses**: Provides meaningful error messages to users instead of stack traces
- **Easier Debugging and Maintenance**: Makes it easier to identify and fix issues in production
- **Consistent Error Handling**: Ensures all errors across the application are handled in a uniform manner

## Key Concepts

### @RestControllerAdvice
The `@RestControllerAdvice` annotation enables global exception handling across the entire application. It intercepts exceptions thrown from both controllers and services, allowing you to define centralized error handling logic.

**Commit:** [Global Handler](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/d868576517063a9f3cfb7d690b6b29a60122d046)

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Exception handlers defined here apply globally
}
```

### @ExceptionHandler
The `@ExceptionHandler` annotation is used within `@RestControllerAdvice` to handle specific exception types. You can define multiple handlers for different exceptions in your global exception handler.

```java
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ApiError> handlerResourceNotFound(ResourceNotFoundException exception) {
    ApiError apiError = ApiError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(exception.getMessage())
                        .build();
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
}
```

### Custom Exception Classes
You can create custom exception classes to represent specific error scenarios in your application. These exceptions can be thrown anywhere in your codebase where that particular error condition occurs.

**Commits:** 
- [cust exception](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/16c7a10ff98cf1641a1e78470942274a788cc330)
- [built own custom exception](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/889829acb4126904c2c7e064c1e43df764b60776)

```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

**Usage:** You can throw this exception from anywhere in your application where a resource is not found.

```java
public void isEmployeeExist(Long id) {
    boolean exists = employeeRepository.existsById(id);
    if (!exists) throw new ResourceNotFoundException("Employee do not Exist :" + id);
}
```

### ApiError Class
The `ApiError` class is used to send custom error responses to the client. Using `@Data` from Lombok provides getters, setters, and other utility methods automatically. You can add more fields to this class to make error responses more informative.

```java
@Data
@Builder
public class ApiError {
    private HttpStatus status;
    private String message;
}
```

## Implementation Examples

### Handling ResourceNotFoundException
**Commit:** [(ResourceNotFound) global exception](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/d40abbada4f05f671c8cf450da9e187fbe991435)

The global exception handler catches `ResourceNotFoundException` thrown from anywhere in the application and returns a 404 response:

```java
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ApiError> handlerResourceNotFound(ResourceNotFoundException exception) {
    ApiError apiError = ApiError.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(exception.getMessage())
                        .build();
    return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
}
```

### Global Exception Handler for Both Controller and Service Layers
**Commit:** [global exept can handle both service and controller](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/0ff1868886987cbd7ecfa8f6c37932c0aa378b1b)

The global exception handler works seamlessly with exceptions thrown from both the service layer and the controller layer. This means you can throw exceptions from your business logic (service) or from your REST endpoints (controller), and they will all be caught and handled consistently.

**Example from Service Layer:**
```java
public void isEmployeeExist(Long id) {
    boolean exists = employeeRepository.existsById(id);
    if (!exists) throw new ResourceNotFoundException("Employee do not Exist :" + id);
}
```

**Example from Controller Layer:**
```java
@GetMapping("/{id}")
public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Long id) {
    Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
    return employeeDTO
            .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
}
```

### Handling Internal Server Errors
**Commit:** [to handle exceptions that get while creating employee i.e.internal server except](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/7388a1f6f87c015afec8add72671d22a01b4b552)

A generic exception handler catches all unexpected exceptions and returns a 500 Internal Server Error response:

```java
@ExceptionHandler(Exception.class)
public ResponseEntity<ApiError> handleInternalServerError(Exception exception) {
    ApiError apiError = ApiError.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(exception.getMessage())
                        .build();
    return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
}
```

### Handling Validation Errors
**Commit:** [handleInputValidationErrors](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/68722d2ec8eaf085f95dd2906c3187813adef59b)

When using `@Valid` on request bodies, validation errors are thrown as `MethodArgumentNotValidException`. This handler collects all validation error messages and returns them:

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ApiError> handleInputValidationErrors(MethodArgumentNotValidException exception) {
    List<String> errors = exception.getBindingResult()
                                        .getFieldErrors()
                                        .stream()
                                        .map(error -> error.getDefaultMessage())
                                        .collect(Collectors.toList());
    
    ApiError apiError = ApiError.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message(errors.toString())
                        .build();
    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
}
```

## Best Practices

### When to Use Try-Catch vs Global Exception Handler
While the global exception handler is excellent for most scenarios, certain critical operations like payment gateway integrations should be handled with try-catch blocks for more granular control. This allows you to implement specific recovery mechanisms or fallback logic for these sensitive operations.

### Extending Error Responses
You can make your error responses more informative by adding additional fields to the `ApiError` class, such as:
- Timestamp
- Error codes
- Path/endpoint information
- Detailed error descriptions

**Example:**
```java
@Data
@Builder
public class ApiError {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private String path;
}
```

## Summary
- Global exception handling with `@RestControllerAdvice` provides a centralized way to manage errors
- Custom exceptions like `ResourceNotFoundException` make your code more expressive and maintainable
- Exception handlers can catch errors from both service and controller layers
- Validation errors are automatically handled when using `@Valid`
- Use try-catch blocks for critical operations that need special error handling (e.g., payment gateways)
