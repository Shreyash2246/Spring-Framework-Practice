# Transforming API Response in Spring Boot

## Overview
Transforming API responses ensures that all responses from your Spring Boot application follow a consistent structure. This approach provides a uniform format for both successful responses and error responses, making the API more predictable and easier to consume.

## Why Transform API Responses?

- **Consistent Response Structure**: All API responses follow the same format, whether success or error
- **Enhanced Error Information**: Include metadata like timestamps with every response
- **Production-Ready**: Professional APIs require standardized response formats
- **Better Client Experience**: Clients can parse responses predictably without checking response types

## Key Components

### GlobalResponseHandler
The `GlobalResponseHandler` class implements `ResponseBodyAdvice<Object>` to intercept and transform all API responses globally. It uses the `@RestControllerAdvice` annotation to apply the transformation across all controllers.

**Commit:** [created handler and res file](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/0141f326def4312ae7149a20e1b56efb094d749d)

```java
@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, 
                          Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, 
                                 MediaType selectedContentType,
                                 Class<? extends HttpMessageConverter<?>> selectedConverterType, 
                                 ServerHttpRequest request,
                                 ServerHttpResponse response) {
        
        if(body instanceof ApiResponse){
            return body;
        }

        return new ApiResponse<>(body);
    }
}
```

#### supports() Method
The `supports()` method determines whether the response handler should be applied to a particular response. When it returns `true`, the handler will intercept and transform every API response across the application.

#### beforeBodyWrite() Method
The `beforeBodyWrite()` method is where the actual transformation happens. It receives the response body as an `Object` and allows you to encapsulate it in any custom return type. In this implementation:
- If the body is already an `ApiResponse`, it returns it as-is to avoid double wrapping
- Otherwise, it wraps the response body in a new `ApiResponse` object

### ApiResponse Class
The `ApiResponse` class is a generic wrapper that provides a consistent structure for all API responses. It contains three fields: `timestamp`, `data`, and `error`. At any given time, either `data` or `error` will be populated, but not both.

```java
@Data
public class ApiResponse<T> {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private T data;
    private ApiError error;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();   
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }
    
    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }
}
```

**Key Features:**
- **Generic Type `<T>`**: Allows wrapping any type of response data
- **Timestamp**: Automatically captures when the response was created
- **Mutual Exclusivity**: Either `data` is populated (success) or `error` is populated (failure), never both
- **JsonFormat**: Applies a custom date-time pattern to the timestamp field

### @JsonFormat for Timestamp
The `@JsonFormat` annotation allows you to define a custom pattern for how the timestamp is serialized to JSON. This ensures that all timestamps in your API responses follow a consistent, human-readable format.

```java
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
private LocalDateTime timestamp;
```

**Example Output:**
```json
{
    "timestamp": "2025-12-30 14:35:22",
    "data": { ... },
    "error": null
}
```

## Integrating with GlobalExceptionHandler

Instead of returning just `ApiError` from the exception handler, you now return `ApiResponse<?>` that wraps the error. This ensures that error responses have the same structure as success responses, including the timestamp field.

**Commit:** [ApiError -> ApiResponse](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/3f1dedef97f0640ed2574d811fa5d9ed792109bc)

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handlerResourceNotFound(ResourceNotFoundException exception) {
        ApiError apiError = ApiError.builder()
                            .status(HttpStatus.NOT_FOUND)
                            .message(exception.getMessage())
                            .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception) {
        ApiError apiError = ApiError.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .message(exception.getMessage())
                            .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInputValidationErrors(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                                            .getFieldErrors()
                                            .stream()
                                            .map(error -> error.getDefaultMessage())
                                            .collect(Collectors.toList());
        
        ApiError apiError = ApiError.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .message(errors.toString())
                            .build();
        return buildErrorResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError), apiError.getStatus());
    }
}
```

## Response Format Examples

### Success Response
When an API call is successful, the response looks like:

```json
{
    "timestamp": "2025-12-30 14:35:22",
    "data": {
        "id": 403,
        "name": "pqrs",
        "email": "pqr@gmail.com",
        "age": 34,
        "salary": 12458.0,
        "role": "USER",
        "dateOfJoining": "2025-10-06",
        "isActive": true
    },
    "error": null
}
```

### Error Response
When an error occurs, the response looks like:

```json
{
    "timestamp": "2025-12-30 14:40:15",
    "data": null,
    "error": {
        "status": "NOT_FOUND",
        "message": "Employee not found with id: 999"
    }
}
```

## Production-Ready Error Handling

With this implementation, your application now has production-ready error handling:
- **Consistent Format**: All responses (success and error) follow the same structure
- **Timestamps**: Every response includes when it was generated
- **Clear Error Information**: Errors include status codes and descriptive messages
- **Automatic Wrapping**: No need to manually wrap responses in controllers
- **Global Application**: Works across all controllers and endpoints

## Summary

Transforming API responses provides a professional, consistent interface for your Spring Boot application:
- `GlobalResponseHandler` intercepts all responses and wraps them in `ApiResponse`
- `supports()` enables the transformation for all API responses
- `beforeBodyWrite()` encapsulates response bodies in the custom format
- `ApiResponse` provides a generic wrapper with timestamp, data, and error fields
- Either `data` or `error` is populated, never both
- `@JsonFormat` ensures consistent timestamp formatting
- `GlobalExceptionHandler` now returns `ApiResponse<?>` instead of just `ApiError`
- The result is a uniform, production-ready API response structure
