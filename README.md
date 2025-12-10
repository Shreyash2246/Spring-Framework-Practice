# Spring Boot Web Demo - Service Layer

## Overview
This project demonstrates the implementation of the Service Layer in Spring Boot, which acts as an intermediary between the Presentation Layer (Controller) and the Persistence Layer (Repository). The service layer handles business logic, validations, and object transformations using ModelMapper.

---

## Service Layer Architecture

The service layer sits between the **Presentation Layer (Controller)** and the **Persistence Layer (Repository)**:

```
Client Request
    ↓
Controller (Presentation Layer)
    ↓
Service Layer (Business Logic)
    ↓
Repository (Persistence Layer)
    ↓
Database
```

---

## Key Roles of Service Layer

### 1. Business Logic
The service layer contains all the business logic of the application. This keeps controllers clean and focused on handling HTTP requests/responses.

**Example:**
```java
public EmployeeDTO createNewEmployee(EmployeeEntity inputEmployee) {
    // Business logic can be added here
    // e.g., admin or employee role check
    EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
    EmployeeEntity savedEmployee = employeeRepository.save(toSaveEntity);
    return modelMapper.map(savedEmployee, EmployeeDTO.class);
}
```

### 2. Validation Checking
Before data reaches the repository or database, the service layer validates it to ensure data integrity and business rules compliance.

**Use Cases:**
- Check if user has permission to perform an action
- Validate business rules (e.g., age must be 18+)
- Ensure data consistency before database operations

### 3. DRY Principle (Don't Repeat Yourself)
The service layer prevents code duplication by centralizing common business logic that multiple controllers might need.

**Benefits:**
- Reusable code across different controllers
- Single source of truth for business operations
- Easier maintenance and updates

---

## ModelMapper

ModelMapper is a library that automatically converts one Java object to another, reducing boilerplate code for manual mapping.

### Purpose
Converts **Entity objects** (database representation) to **DTO objects** (data transfer representation) and vice versa.

**Why Use ModelMapper?**
- Eliminates manual field-by-field copying
- Reduces code duplication
- Automatic mapping based on field names
- Type-safe object conversion

### Configuration

To use ModelMapper, create a **@Configuration** class with a **@Bean** method:

```java
@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }
}
```

**Key Points:**
- `@Configuration` marks the class as a Spring configuration class
- `@Bean` tells Spring to manage the ModelMapper instance
- Once configured, ModelMapper can be injected anywhere in the application

### Usage in Service Layer

```java
@Service
public class employeeService {

    final EmployeeRepository employeeRepository;
    final ModelMapper modelMapper;

    public employeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
        // Convert Entity to DTO
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
    }

    public List<EmployeeDTO> getAllEmployees(Integer age, String sortby) {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        // Convert list of Entities to list of DTOs using streams
        return employeeEntities.stream()
                .map(entity -> modelMapper.map(entity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }
}
```

---

## Service and Controller Return Types

Both **Service** and **Controller** use **DTO** as the return type instead of Entity.

**Why Return DTO Instead of Entity?**
- **Security**: Prevents exposing sensitive database fields to clients
- **Decoupling**: Separates internal database structure from external API
- **Flexibility**: Can modify database structure without breaking API contracts
- **Controlled Data**: Send only necessary fields to the client

**Flow:**
```
Repository → Entity → Service (converts to DTO) → Controller → Client (receives DTO)
```

**Example:**
```java
// Service returns DTO
public EmployeeDTO getEmployeeById(Long id) {
    EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);
    return modelMapper.map(employeeEntity, EmployeeDTO.class);
}

// Controller also returns DTO
@GetMapping("/{id}")
public EmployeeDTO getEmployeeById(@PathVariable("id") Long id) {
    return employeeService.getEmployeeById(id);
}
```

---

## Important Annotations

### `@Service`
Marks a class as a service component in Spring. This annotation:
- Registers the class as a Spring bean
- Makes it available for dependency injection
- Indicates that the class contains business logic

**Example:**
```java
@Service
public class employeeService {
    // Service layer implementation
}
```

### `@Configuration`
Marks a class as a source of bean definitions for Spring's application context.

**Example:**
```java
@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
```

---

## Project Structure After Service Layer

```
src/main/java/com/springbootwebtutorial/web_demo/
├── configs/
│   └── MapperConfig.java
├── controllers/
│   └── EmployeeController.java
├── dto/
│   └── EmployeeDTO.java
├── entities/
│   └── EmployeeEntity.java
├── repositories/
│   └── EmployeeRepository.java
├── services/
│   └── employeeService.java
└── WebDemoApplication.java
```

---

## Key Learnings

- The service layer acts as a bridge between the controller and repository
- It centralizes business logic, validation, and data transformation
- ModelMapper automates object conversion between Entity and DTO
- Service layer promotes code reusability and follows the DRY principle
- Both service and controller return DTOs to protect sensitive data
- `@Service` annotation registers the service class as a Spring bean
- `@Configuration` and `@Bean` are used to configure third-party libraries like ModelMapper

---

## Maven Dependency

To use ModelMapper, add this dependency to `pom.xml`:

```xml
<dependency>
    <groupId>org.modelmapper</groupId>
    <artifactId>modelmapper</artifactId>
    <version>3.2.0</version>
</dependency>
```

---
