# Spring Boot REST API - Learning Journey

A comprehensive guide to building a REST API with Spring Boot, covering the complete MVC architecture with PUT, PATCH, and DELETE operations.


## GET Mapping

### Basic GET Mapping

**Commit:** [getMapping](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/83ed79f24e9738ad7fcc8f8e6f421b86b7bcb7ff)

`@GetMapping` is used to handle HTTP GET requests. It retrieves data from the server.

```java
@GetMapping("/{id}")
public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Long id) {
    Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
    return employeeDTO
            .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
            .orElse(ResponseEntity.notFound().build());
}
```

**Endpoint:** `http://localhost:9090/employees/1`

---

### GET with Query Parameters

**Commits:**
- [@RequestParam](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/06ced0be3e64545ef9140a5a8a533a0b4d718714)
- [name param use](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/0b35a0138cd66828ac91da9d5c81c6e09b26c930)

`@RequestParam` is used to extract query parameters from the URL.

```java
@GetMapping
public ResponseEntity<List<EmployeeDTO>> getAllEmployees(
        @RequestParam(required = false) Integer age,
        @RequestParam(required = false) String sortby) {
    return ResponseEntity.ok(employeeService.getAllEmployees(age, sortby));
}
```

**Endpoint:** `http://localhost:9090/employees?age=25&sortby=name`

- `required = false` makes the parameter optional
- Can accept multiple query parameters

---

## Persistence Layer

**Commits:**
- [persistence layer & manipulating using repository](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/e14ea3dd02bedec23780f4c3f745b858903540ed)
- [mySql configuration](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/28b0dab78d9d79a49b06d517f365755f3a3955db)

The persistence layer handles database operations using JPA and repositories.

### Entity Class

```java
@Entity
@Table(name = "employees")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private int age;
    private LocalDate dateOfJoining;
    private boolean isActive;
}
```

### Repository Interface

```java
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}
```

**Key Points:**
- `@Entity` marks the class as a JPA entity
- `@Table` specifies the database table name
- `JpaRepository` provides CRUD operations out of the box
- Repository handles all database interactions

---

## Service Layer

**Commit:** [service layer](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/b38b82ebd9f14a10257b2f1c20f8e3749b398b01)

The service layer contains business logic and acts as an intermediary between the controller and repository.

```java
@Service
public class employeeService {
    final EmployeeRepository employeeRepository;
    final ModelMapper modelMapper;

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(entity -> modelMapper.map(entity, EmployeeDTO.class));
    }

    public EmployeeDTO createNewEmployee(EmployeeEntity inputEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmployee = employeeRepository.save(toSaveEntity);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }
}
```

**Architecture:**
- **Controller** → handles HTTP requests and responses
- **Service Layer** → contains business logic
- **Repository** → handles database operations

**Benefits:**
- Separation of concerns
- Controller doesn't directly interact with repository
- Business logic is centralized
- Easy to test and maintain

---

## PUT Mapping

**Commit:** [updating emp by id](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/c55b0041d2fc45a300621db6d9b5414568147e4b)

`@PutMapping` is used to update an entire resource. It replaces the complete data of an existing record.

### Controller

```java
@PutMapping(path = "/{id}")
public ResponseEntity<EmployeeDTO> updateEmployee(
        @RequestBody EmployeeDTO employeeDTO, 
        @PathVariable Long id) {
    return ResponseEntity.ok(employeeService.getEmployeeById(id, employeeDTO));
}
```

### Service Layer

```java
public EmployeeDTO getEmployeeById(Long id, EmployeeDTO employeeDTO) {
    EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
    employeeEntity.setId(id);
    EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
    return modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
}
```

**Key Points:**
- PUT updates all fields of the resource
- If the ID doesn't exist, you can implement auto-insertion logic
- `@RequestBody` converts JSON to Java object
- `@PathVariable` extracts the ID from the URL

**Endpoint:** `http://localhost:9090/employees/3`

---

## DELETE Mapping

**Commit:** [@deleteMapping](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/af70fbe5dd4f1d92ee1ced7dc570642f3c4e57f5)

`@DeleteMapping` is used to delete a resource from the database.

### Controller

```java
@DeleteMapping(path = "/{id}")
public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long id) {
    boolean gotDeleted = employeeService.deleteEmployeeById(id);
    if (!gotDeleted) {
        return ResponseEntity.notFound().build();
    } else {
        return ResponseEntity.ok(true);
    }
}
```

### Service Layer

```java
public Boolean deleteEmployeeById(Long id) {
    boolean exists = isEmployeeExist(id);
    if (!exists) {
        return false;
    }
    employeeRepository.deleteById(id);
    return true;
}

public boolean isEmployeeExist(Long id) {
    return employeeRepository.existsById(id);
}
```

**Logic:**
1. Check if the employee exists by ID
2. If exists → delete and return `true`
3. If not exists → return `false`

**Endpoint:** `http://localhost:9090/employees/3`

---

## PATCH Mapping

**Commit:** [@pathMapping](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/c1945afa975b49d7e72d6771f90337287f0ef7f5)

`@PatchMapping` is used for partial updates. Unlike PUT, it only updates specific fields instead of replacing the entire resource.

### Controller

```java
@PatchMapping(path = "/{id}")
public ResponseEntity<EmployeeDTO> patchEmployee(
        @RequestBody Map<String, Object> updates, 
        @PathVariable Long id) {
    EmployeeDTO employeeDTO = employeeService.patchEmployee(id, updates);
    if (employeeDTO == null) {
        return ResponseEntity.notFound().build();
    } else {
        return ResponseEntity.ok(employeeDTO);
    }
}
```

### Service Layer (Using Reflection)

```java
public EmployeeDTO patchEmployee(Long id, Map<String, Object> updates) {
    boolean exists = isEmployeeExist(id);
    if (!exists) return null;
    
    EmployeeEntity employeeEntity = employeeRepository.findById(id).get();
    
    updates.forEach((field, value) -> {
        Field fieldToUpdate = ReflectionUtils.getRequiredField(EmployeeEntity.class, field);
        fieldToUpdate.setAccessible(true);
        ReflectionUtils.setField(fieldToUpdate, employeeEntity, value);
    });
    
    return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);
}
```

**How It Works:**
1. Check if the employee exists
2. Fetch the existing employee entity
3. Use **Java Reflection** to dynamically access and update specific fields
4. `setAccessible(true)` allows modification of private fields
5. Save the updated entity

**Request Body Example:**
```json
{
    "name": "Updated Name",
    "age": 30
}
```

Only the fields provided in the request will be updated.

**Endpoint:** `http://localhost:9090/employees/3`

---

## Response Entity & Status Codes

**Commit:** [Added Status Codes to Endpoints](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/87cde632ba0f77bd3955c4ef16fdc77e795bdb33)

`ResponseEntity` is used to return HTTP status codes along with response data from the controller.

### Common Status Codes

```java
// 200 OK - Request successful
return ResponseEntity.ok(employeeDTO);

// 201 CREATED - Resource created successfully
return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);

// 404 NOT FOUND - Resource not found
return ResponseEntity.notFound().build();
```

### Examples in Controller

#### GET with Status Codes
```java
@GetMapping("/{id}")
public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Long id) {
    Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
    return employeeDTO
            .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))  // 200 OK
            .orElse(ResponseEntity.notFound().build());            // 404 NOT FOUND
}
```

#### POST with 201 Created
```java
@PostMapping("/create")
public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody EmployeeEntity inputEmployee) {
    EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
    return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);  // 201 CREATED
}
```

#### DELETE with Boolean Response
```java
@DeleteMapping(path = "/{id}")
public ResponseEntity<Boolean> deleteEmployee(@PathVariable Long id) {
    boolean gotDeleted = employeeService.deleteEmployeeById(id);
    if (!gotDeleted) {
        return ResponseEntity.notFound().build();  // 404 NOT FOUND
    } else {
        return ResponseEntity.ok(true);            // 200 OK
    }
}
```

**Benefits:**
- Provides clear HTTP status codes to clients
- Better API design following REST standards
- Easier error handling on the client side

---

## Summary

### MVC Architecture Flow

```
Client Request
     ↓
Controller (@RestController)
     ↓
Service Layer (@Service)
     ↓
Repository (JpaRepository)
     ↓
Database
```

### Key Concepts Learned

- **@GetMapping** - Retrieve data
- **@PostMapping** - Create new resource
- **@PutMapping** - Update entire resource
- **@PatchMapping** - Partial update using Reflection
- **@DeleteMapping** - Delete resource
- **ResponseEntity** - Return status codes with response
- **Service Layer** - Business logic separation
- **Repository** - Database operations
- **DTO Pattern** - Data Transfer Objects for clean API responses

---

## Project Structure

```
src/
├── main/
│   ├── java/com/springbootwebtutorial/web_demo/
│   │   ├── controllers/
│   │   │   └── EmployeeController.java
│   │   ├── services/
│   │   │   └── employeeService.java
│   │   ├── repositories/
│   │   │   └── EmployeeRepository.java
│   │   ├── entities/
│   │   │   └── EmployeeEntity.java
│   │   ├── dto/
│   │   │   └── EmployeeDTO.java
│   │   └── configs/
│   │       └── MapperConfig.java
│   └── resources/
│       └── application.properties
```

---

## Technologies Used

- **Spring Boot** - Application framework
- **Spring Data JPA** - Database abstraction
- **MySQL** - Database
- **Lombok** - Reduce boilerplate code
- **ModelMapper** - Object mapping between Entity and DTO
- **Java Reflection** - Dynamic field updates in PATCH

---
