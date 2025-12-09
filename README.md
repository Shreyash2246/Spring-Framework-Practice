# Presentation Layer - Spring Boot REST API

## Overview

The Presentation Layer is responsible for handling communication between the client and server through Controllers. This layer receives HTTP requests from clients, processes them, and returns appropriate responses.

## @RestController Annotation

### What is @RestController?

`@RestController` is a specialized annotation used to create RESTful web services in Spring Boot. It combines `@Controller` and `@ResponseBody`, automatically converting return values to JSON/XML.

**Key Points:**
- While `@Controller` is available, `@RestController` is preferred for building REST APIs
- Automatically serializes return objects to JSON using Jackson
- Eliminates the need to annotate each method with `@ResponseBody`

**Example:**
```java
@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
    // Controller methods
}
```

**📝 Commit:** [getMapping](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/83ed79f24e9738ad7fcc8f8e6f421b86b7bcb7ff)

---

## Request Mapping

### @GetMapping

`@GetMapping` is used to map HTTP GET requests to specific handler methods. The `path` parameter defines the URI (Uniform Resource Identifier) that clients use to access the endpoint.

**How it Works:**
1. Client sends a request to a specific URL
2. Component Scanner in Spring scans for controllers
3. Dispatcher Servlet maps the request to the appropriate handler method

**Example:**
```java
@GetMapping(path = "/{employeeId}")
public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id) {
    return new EmployeeDTO(id, "John Doe", "john.doe@example.com", 30, LocalDate.of(2020, 1, 15), true);
}
```

**Request URL:** `GET http://localhost:8080/employees/1`

**Response (JSON):**
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "age": 30,
  "dateOfJoining": "2020-01-15",
  "isActive": true
}
```

**📝 Commit:** [getMapping demo endpoint](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/bcaaf6b1ab30fa8a254429c2cade3cd59986af3a)

---

## DTO (Data Transfer Object)

### What is DTO?

DTO stands for **Data Transfer Object**. It is a simple Java object used to transfer data between different layers of the application, specifically between:
- Client ↔ Controller
- Controller ↔ Service Layer

**Key Characteristics:**
- Also known as POJO (Plain Old Java Object)
- Contains only data fields with getters and setters
- No business logic
- Used to decouple layers and control what data is exposed to clients

**Example:**
```java
public class EmployeeDTO {
    private long id;
    private String name;
    private String email;
    private int age;
    private LocalDate dateOfJoining;
    private boolean isActive;

    // Constructors
    public EmployeeDTO() {
    }

    public EmployeeDTO(long id, String name, String email, int age, 
                       LocalDate dateOfJoining, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.dateOfJoining = dateOfJoining;
        this.isActive = isActive;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    // ... other getters and setters
}
```

**Why Use DTO?**
- Separates internal data models (Entities) from external API contracts
- Reduces data exposure (security)
- Jackson automatically converts DTO to JSON format

---

## Dynamic URL Paths

### 1. @PathVariable

`@PathVariable` is used to extract values from the URI path. These are **essential parameters** that are part of the URL structure.

**Use Cases:**
- Identifying specific resources (e.g., employee ID, product ID)
- Required parameters that must be present in the URL

**Example:**
```java
// GET http://localhost:8080/employees/{employeeId}
@GetMapping(path = "/{employeeId}")
public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeId") Long id) {
    return new EmployeeDTO(id, "John Doe", "john.doe@example.com", 30, 
                          LocalDate.of(2020, 1, 15), true);
}
```

**Important Notes:**
- The `name` parameter in `@PathVariable` should match the placeholder in the URL path
- If names match, you can omit the `name` attribute: `@PathVariable Long employeeId`
- Response is automatically converted to JSON by Jackson

**📝 Commit:** [name param use](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/0b35a0138cd66828ac91da9d5c81c6e09b26c930)

---

### 2. @RequestParam

`@RequestParam` is used to extract query parameters from the URL. These are **optional parameters** typically used for filtering, sorting, or pagination.

**Use Cases:**
- Filtering data (e.g., by age, status)
- Sorting results
- Pagination
- Optional search criteria

**Example:**
```java
// GET http://localhost:8080/employees?EmployeeAge=25&sortby=name
@GetMapping
public String getEmployees(@RequestParam(required = false, name = "EmployeeAge") Integer age, 
                          @RequestParam(required = false) String sortby) {
    return "The age is " + age + " and sortby is " + sortby;
}
```

**Parameters Explained:**
- `required = false`: Makes the parameter optional
- `name = "EmployeeAge"`: Maps request param `EmployeeAge` to method param `age`
- If `name` is not specified, the method parameter name is used

**Request Examples:**
```
GET /employees?EmployeeAge=25&sortby=name
GET /employees?EmployeeAge=30
GET /employees?sortby=email
GET /employees  (both params optional)
```

**📝 Commit:** [@RequestParam](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/06ced0be3e64545ef9140a5a8a533a0b4d718714)

---

### 3. @RequestBody

`@RequestBody` is used to pass complex data in the request body, typically in JSON format. This is essential for POST, PUT, and PATCH requests.

**Use Cases:**
- Creating new resources (POST)
- Updating existing resources (PUT/PATCH)
- Sending complex nested objects
- Handling multiple fields in a single request

**Example:**
```java
@PostMapping
public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
    // Process the employee data
    // Save to database via service layer
    return employeeDTO;
}
```

**Request:**
```
POST http://localhost:8080/employees
Content-Type: application/json

{
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "age": 28,
  "dateOfJoining": "2023-03-10",
  "isActive": true
}
```

**How it Works:**
- Jackson automatically deserializes JSON to Java object
- The DTO object is populated with request data
- Validation can be applied using annotations like `@Valid`

---

## Other HTTP Mapping Annotations

While this module focuses on `@GetMapping`, Spring Boot provides mappings for all HTTP methods:

| Annotation | HTTP Method | Purpose |
|------------|-------------|---------|
| `@GetMapping` | GET | Retrieve data |
| `@PostMapping` | POST | Create new resources |
| `@PutMapping` | PUT | Update/replace existing resources |
| `@PatchMapping` | PATCH | Partially update resources |
| `@DeleteMapping` | DELETE | Remove resources |

**Example:**
```java
@PostMapping
public EmployeeDTO createEmployee(@RequestBody EmployeeDTO dto) { }

@PutMapping("/{id}")
public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO dto) { }

@DeleteMapping("/{id}")
public void deleteEmployee(@PathVariable Long id) { }
```

---

## Testing REST APIs

### Browser Limitations

By default, browsers treat every request as a **GET** request. To test other HTTP methods (POST, PUT, DELETE), you need to:

1. **Use API Testing Tools:**
   - Postman
   - Insomnia
   - cURL
   - Thunder Client (VS Code extension)

2. **Define in Frontend:**
   ```javascript
   // JavaScript fetch example
   fetch('http://localhost:8080/employees', {
     method: 'POST',
     headers: {
       'Content-Type': 'application/json'
     },
     body: JSON.stringify({
       name: 'John Doe',
       email: 'john@example.com',
       age: 30
     })
   })
   ```

---

## JSON Conversion

### Jackson Library

Spring Boot uses **Jackson** library to automatically convert between Java objects and JSON:

**Java Object → JSON (Serialization):**
```java
// Controller returns EmployeeDTO object
return new EmployeeDTO(1, "John", "john@email.com", 30, LocalDate.now(), true);

// Jackson converts to:
{
  "id": 1,
  "name": "John",
  "email": "john@email.com",
  "age": 30,
  "dateOfJoining": "2025-12-09",
  "isActive": true
}
```

**JSON → Java Object (Deserialization):**
```java
// Client sends JSON in request body
// Jackson automatically converts to EmployeeDTO object
@PostMapping
public EmployeeDTO create(@RequestBody EmployeeDTO dto) {
    // dto is now a populated Java object
}
```

---

## Key Takeaways

✅ **@RestController** combines controller and response body functionality  
✅ **@GetMapping** maps HTTP GET requests to handler methods  
✅ **DTO** transfers data between layers while keeping them decoupled  
✅ **@PathVariable** extracts required parameters from URL paths  
✅ **@RequestParam** handles optional query parameters for filtering/sorting  
✅ **@RequestBody** processes complex JSON data in request body  
✅ **Jackson** automatically converts Java objects to JSON and vice versa  
✅ Use **Postman or similar tools** to test non-GET HTTP methods  

---

- Jackson (JSON processing)
- Maven
