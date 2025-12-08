# Spring Boot Web Development

## Overview

This project demonstrates the fundamentals of building web services using Spring Boot, including RESTful API development and MVC architecture patterns.

## Web Services Architecture

### Client-Server Communication

**Client** → **Web Services (APIs)** → **Database**

- Clients interact with web services through APIs
- Data JPA is used to manage database operations
- Web services act as an intermediary layer between clients and the database

### Types of Web Service Protocols

While building web services, there are several protocol options available:

- **REST API** (Most commonly used)
- **SOAP** (Simple Object Access Protocol)
- **WebSocket** (For real-time, bidirectional communication)

## REST APIs

### Key Characteristics

- **Stateless**: REST APIs do not maintain any information about previous requests. Each request is independent and contains all necessary information.

### HTTP Request Methods

REST APIs use different HTTP methods to perform various operations:

| Method | Purpose |
|--------|---------|
| `GET` | Retrieve data from the server |
| `POST` | Create new resources |
| `PUT` | Update/replace existing resources |
| `PATCH` | Partially update existing resources |
| `DELETE` | Remove resources |

**Example:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        // Retrieve user by ID
    }
    
    @PostMapping
    public User createUser(@RequestBody User user) {
        // Create new user
    }
    
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        // Update existing user
    }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        // Delete user
    }
}
```

## Spring Boot Starter Web

`spring-boot-starter-web` is a comprehensive dependency that includes everything needed to build web applications.

### Components Included:

- **Spring Boot Starter**: Core Spring Boot functionality
- **Jackson**: JSON serialization/deserialization library
- **Spring Core**: Foundation of the Spring Framework
- **Embedded Tomcat**: Web server for running applications

**Maven Dependency:**
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## Spring Boot MVC Architecture

### Request Flow

**Client** → **Controller** (DTO, Services, Logging) → **Service Layer** (Business Logic) → **Repository** (Entities) → **Database**

### Architecture Diagram
```
┌────────┐     ┌────────────┐     ┌───────────────┐     ┌────────────┐     ┌──────────┐
│ Client │ --> │ Controller │ --> │ Service Layer │ --> │ Repository │ --> │ Database │
└────────┘     └────────────┘     └───────────────┘     └────────────┘     └──────────┘
                  (DTO)              (Business Logic)       (Entities)
```

## Layered Architecture

### 1. Presentation Layer (Controller)

**Responsibilities:**
- Handle HTTP requests and responses
- Authentication and authorization
- JSON translation (converting JSON to Java objects and vice versa)
- Input validation at the API level

**Example:**
```java
@RestController
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
```

### 2. Business Layer (Service)

**Responsibilities:**
- Business logic implementation
- Data validation
- Authorization checks
- Transaction management

**Example:**
```java
@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<ProductDTO> getAllProducts() {
        // Business logic: validation, processing
        List<Product> products = productRepository.findAll();
        return products.stream()
                      .map(this::convertToDTO)
                      .collect(Collectors.toList());
    }
    
    private ProductDTO convertToDTO(Product product) {
        // Convert entity to DTO
    }
}
```

### 3. Persistence Layer (Repository)

**Responsibilities:**
- Storage logic and database interaction
- Database drivers management
- Hibernate for CRUD operations
- Translating JPQL (Java Persistence Query Language) to SQL

**Example:**
```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Hibernate handles basic CRUD operations
    // Custom queries can be added as needed
    
    List<Product> findByCategory(String category);
}
```

**Entity Example:**
```java
@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String category;
    private Double price;
    
    // Getters and setters
}
```

## Benefits of MVC Architecture

### 1. Separation of Concerns
Each layer has a distinct responsibility, making code more organized and easier to understand.

### 2. Reusability
Business logic in the service layer can be reused across multiple controllers without duplication.

### 3. Testability
Each layer can be tested independently:
- Controllers can be tested with MockMvc
- Services can be tested with mocked repositories
- Repositories can be tested with test databases

### 4. Scalability
Individual layers can be scaled independently based on application needs.

## Spring Boot Web Project Structure

### Data Flow

**Client** (HTTP Request) → **Controller** (DTO) → **Services** (CRUD Operations) → **Entity** → **Database**

### Component Roles:

- **DTO (Data Transfer Object)**: Simplified objects for transferring data between layers
- **Entity**: Actual database table representation with JPA annotations
- **Service**: Contains business logic and orchestrates data flow
- **Controller**: Handles HTTP requests and returns responses

**Complete Flow Example:**
```java
// DTO
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
}

// Entity
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private String category;
    private LocalDateTime createdAt;
}

// Repository
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

// Service
@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    
    public ProductDTO createProduct(ProductDTO dto) {
        Product product = convertToEntity(dto);
        Product saved = repository.save(product);
        return convertToDTO(saved);
    }
}

// Controller
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService service;
    
    @PostMapping
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO dto) {
        ProductDTO created = service.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
```

---

## Technologies Used

- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Maven
