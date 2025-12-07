# Spring Framework - Dependency Injection (DI) Learning Journey

This repository documents my hands-on learning experience with Spring Framework's Dependency Injection concepts. Each section includes practical implementations with corresponding commit links.

---

## 1. What is Dependency Injection (DI)?

Dependency Injection is a design pattern managed by the Spring Framework where dependencies are provided to a class rather than the class creating them itself.

### Real-World Analogy: Bob and Alice's Pastry Shop
- **Alice** is a pastry chef who makes cakes
- **Bob** provides all the materials (ingredients, tools) that Alice needs
- Alice doesn't have to worry about getting the materials - they are **injected** to her
- This is how DI works: Spring (Bob) provides all the dependencies to your class (Alice)

### Key Learning Points:
- Spring Framework manages object creation and dependency injection
- Classes receive their dependencies instead of creating them
- Promotes separation of concerns and cleaner code architecture

**Related Commits:**
- [Initial commit: Spring Framework setup](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/0a4adefc3cab4f6291667160820c55fa09c847ac)
- [We can do without new keyword](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/33ced7ea0d195ffd2b55857187c6d0df35691be2)
- [Added autowired and component annotation](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/6292a4232385608538c7744de132568cc28fd1e3)

---

## 2. Tightly Coupled vs Loosely Coupled Systems

### Tightly Coupled System
A system where components are directly dependent on specific implementations, limiting flexibility and reusability.

**Characteristics:**
- Limited to only one specific task
- Hard to modify or extend
- Direct dependencies between classes
- Difficult to test in isolation

**Commit:** [Tightly coupled](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/8f393ad366bfa42aee846b360a08701d2abb8de4)

### Loosely Coupled System
A system where components depend on abstractions (interfaces) rather than concrete implementations, providing flexibility and multiple functionalities.

**Characteristics:**
- Can provide multiple functionalities
- Easy to switch between different implementations
- Uses interfaces as contracts
- Better testability and maintainability

**Implementation Strategy:**
1. Create a **specification interface** that defines the contract
2. Implement the interface with various functionalities
3. Use Spring annotations to manage dependencies

#### Key Annotations:

**`@Primary`** - Sets bean priority when multiple implementations exist
```java
@Component
@Primary
public class devDb implements specificationDB {
    // This implementation will be injected by default
}
```

**`@Autowired`** - Injects the interface implementation automatically
```java
@Autowired
specificationDB db;
```

**`@ConditionalOnProperty`** - Differentiates bean functionality based on environment variables

**Example:**
```java
@Component
@ConditionalOnProperty(name = "deploy.env", havingValue = "development")
public class devDb implements specificationDB {
    public String getData() {
        return "dev DB!";
    }
}

@Component
@ConditionalOnProperty(name = "deploy.env", havingValue = "production")
public class prodDb implements specificationDB {
    public String getData() {
        return "prod DB!";
    }
}
```

**application.properties:**
```properties
deploy.env=production
```

**Debugging Tip:**
Use `debug=true` in `application.properties` to see which conditions matched and unmatched during Spring context initialization.

**Related Commits:**
- [Loosely coupled with @Primary](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/314f88592e4b4c6457382bfa44021033ebe95915)
- [Loosely coupled with env variable and @ConditionalOnProperty](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/0ead2f75d3732dde9a28f2b7ce5c8d0ca4c224ee)
- [Used debugger to see matched conditions](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/e9707c66d046452c900a101d09a6ccef510b3396)

---

## 3. Benefits of Dependency Injection

### 1. Loosely Coupled Architecture
- Components depend on abstractions, not concrete implementations
- Easy to swap implementations without changing dependent code
- Better code organization and maintainability

### 2. Flexible Configuration
- Change application behavior through configuration files
- Switch between different implementations using properties
- Environment-specific configurations (dev, prod, test)

### 3. Improved Testability
- Easy to mock dependencies during testing
- No need to write separate code for each component when testing
- Better than traditional approaches (like in MERN stack) where separate test code is required for each component

**Example - Constructor Injection for Testing:**
```java
@Service
public class dbService {
    final specificationDB db;

    // Constructor injection makes it easy to inject mock objects during testing
    public dbService(specificationDB db) {
        this.db = db;
    }

    public String getData() {
        return db.getData();
    }
}
```

**Related Commits:**
- [Explicit bean declaration](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/a123d6f0fcafe1748b13fbf09b03af883c02add5)
- [Used bean lifecycle hooks](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/8db9ac993556d06b1aa7ef7ba5ba2d359dbbbd34)
- [Singleton scope](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/3e5339e9c8d03af47dada020c0afece1c156200a)
- [Prototype scope](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/e109cf7ffc7a663d28414c991ee98f7c095be1dc)

---

## 4. Constructor Injection (CI)

Constructor Injection is the **most recommended** and widely used form of dependency injection in Spring Framework.

### Why Constructor Injection?
- **Immutability**: Dependencies are marked as `final`, ensuring they cannot be changed after object creation
- **Mandatory dependencies**: Forces all required dependencies to be provided at construction time
- **Better than Field Injection** (`@Autowired` on fields): Promotes immutability and makes dependencies explicit

### Implementation:
```java
@Service
public class dbService {
    final specificationDB db;  // final keyword ensures immutability

    // Constructor injection
    public dbService(specificationDB db) {
        this.db = db;
    }

    public String getData() {
        return db.getData();
    }
}
```

### Advantages over Field Injection:
```java
// ❌ Field Injection (not recommended)
@Autowired
private specificationDB db;  // Cannot be final, mutable

// ✅ Constructor Injection (recommended)
final specificationDB db;
public dbService(specificationDB db) {
    this.db = db;
}
```

**Commit:** [Constructor Injection](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/6c6d5352a4fe6c1fd995ce0763fbd441153328b3)

---

## Project Structure

```
src/main/java/com/Spring_Framework/Practice/Spring_Framework/
├── SpringFrameworkApplication.java
├── dbExample/
│   ├── specificationDB.java      # Interface
│   ├── devDb.java                 # Development implementation
│   ├── prodDb.java                # Production implementation
│   └── dbService.java             # Service using DI
```

---


## Learning Progress

This project demonstrates progressive learning of Spring Framework concepts:
1. ✅ Understanding Dependency Injection fundamentals
2. ✅ Implementing tightly and loosely coupled systems
3. ✅ Using Spring annotations (`@Component`, `@Service`, `@Autowired`, `@Primary`, `@ConditionalOnProperty`)
4. ✅ Constructor Injection for immutability
5. ✅ Environment-based configuration
6. ✅ Bean scopes and lifecycle management

---

