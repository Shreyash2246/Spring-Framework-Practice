# Spring Boot Web Demo - JPA & Persistence Layer

## Overview
This project demonstrates the implementation of JPA (Java Persistence API) and the persistence layer in Spring Boot, including entity management, repository interactions, and MySQL database configuration.

---

## Java Persistence API (JPA)

JPA is a specification that provides an abstraction layer for database operations in Java applications. The flow works as follows:

**JPA Architecture Flow:**
```
Java Application
    ↓
JPA / JPQL (Java Persistence Query Language)
    ↓
JPA Provider (Hibernate, EclipseLink)
    ↓
ORM (Object-Relational Mapping)
    ↓
Database Driver (MySQL Connector, H2 Driver)
    ↓
Database (MySQL, H2)
```

**Key Components:**
- **JPQL**: Query language similar to SQL but works with Java objects
- **JPA Provider**: Implementation of JPA specification (Hibernate is most common)
- **ORM**: Maps Java objects to database tables automatically
- **Driver**: Connects to the actual database using URL, username, and password

---

## Entity vs DTO (Data Transfer Object)

### Entity
- Represents a table in the database
- Annotated with `@Entity` to mark it as a JPA entity
- Fields are mapped to table columns
- Contains sensitive data related to the entity
- Used for database operations

**Example:**
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

### DTO
- Used for data transfer between layers (Controller ↔ Service ↔ Repository)
- Contains validation logic
- Does not interact directly with the database
- Provides data encapsulation and security

**Commit:** [persistence layer & manipulating using repository](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/e14ea3dd02bedec23780f4c3f745b858903540ed)

---

## JPA Annotations

### `@Entity`
Marks a class as a JPA entity that will be mapped to a database table.

### `@Table(name = "table_name")`
Specifies the table name in the database. If not provided, the class name is used as the table name.

### `@Id`
Marks a field as the primary key of the entity.

### `@GeneratedValue(strategy = GenerationType.AUTO)`
Specifies how the primary key should be generated:
- `AUTO`: JPA provider chooses the strategy
- `IDENTITY`: Database auto-increment
- `SEQUENCE`: Uses database sequence
- `TABLE`: Uses a separate table for ID generation

**Example:**
```java
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
```

---

## JPA Repository Interface

The repository interface extends `JpaRepository` and provides built-in CRUD operations without needing to write implementation code.

**Key Features:**
- Handles all CRUD operations (Create, Read, Update, Delete)
- Provides methods like `findById()`, `findAll()`, `save()`, `delete()`
- Supports complex custom queries using method naming conventions
- Directly interacts with the database

**Example:**
```java
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    // JpaRepository provides methods like:
    // save(), findById(), findAll(), deleteById(), etc.
}
```

**Note:** While we directly injected the repository into the controller for practice, **it is not a best practice**. In production applications, a service layer should be used between the controller and repository.

**Commit:** [persistence layer & manipulating using repository](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/e14ea3dd02bedec23780f4c3f745b858903540ed)

---

## Optional Class

The `Optional` class is used to handle potential null values safely, avoiding `NullPointerException`.

**Example:**
```java
@GetMapping("/{id}")
public EmployeeEntity getEmployeeById(@PathVariable("id") Long id) {
    return employeeRepository.findById(id).orElse(null);
}
```

- `findById()` returns `Optional<EmployeeEntity>`
- `orElse(null)` returns the entity if found, otherwise returns `null`
- Other useful methods: `orElseThrow()`, `isPresent()`, `ifPresent()`

---

## Lombok

Lombok is a library that reduces boilerplate code by auto-generating getters, setters, constructors, and other common methods at compile time.

**Commonly Used Annotations:**
- `@Getter`: Generates getter methods for all fields
- `@Setter`: Generates setter methods for all fields
- `@AllArgsConstructor`: Generates a constructor with all fields as parameters
- `@NoArgsConstructor`: Generates a no-argument constructor (required by JPA)

**Example:**
```java
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    // No need to write getters, setters, or constructors manually
}
```

---

## MySQL & JPA Configuration

Configuration is done in `application.properties` to connect Spring Boot with MySQL database.

**Example Configuration:**
```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/web_demo_db
spring.datasource.username=springpract
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true
```

**Configuration Properties Explained:**
- `spring.datasource.url`: JDBC URL for MySQL database connection
- `spring.datasource.username`: Database username
- `spring.datasource.password`: Database password
- `spring.datasource.driver-class-name`: MySQL driver class
- `spring.jpa.hibernate.ddl-auto=update`: Automatically updates database schema based on entities
- `spring.jpa.show-sql=true`: Shows SQL queries in console logs
- `spring.jpa.properties.hibernate.dialect`: Specifies MySQL dialect for Hibernate

**Commit:** [mySql configuration](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/28b0dab78d9d79a49b06d517f365755f3a3955db)

---

## Project Structure

```
src/main/java/com/springbootwebtutorial/web_demo/
├── controllers/
│   └── EmployeeController.java
├── dto/
│   └── EmployeeDTO.java
├── entities/
│   └── EmployeeEntity.java
├── repositories/
│   └── EmployeeRepository.java
└── WebDemoApplication.java
```
