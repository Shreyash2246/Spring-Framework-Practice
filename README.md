# Spring Framework Bean Learning Journey 🌱

This repository documents my hands-on learning of **Bean Creation and Management** in Spring Framework. Each commit in the [Bean branch](https://github.com/Shreyash2246/Spring-Framework-Practice/commits/Bean/) represents a progressive step in understanding Spring's core concepts.

---

## 📚 Table of Contents

1. [What is a Bean?](#what-is-a-bean)
2. [XML vs Annotation-Based Configuration](#xml-vs-annotation-based-configuration)
3. [Ways to Define Beans](#ways-to-define-beans)
   - [Annotation-Based Approach](#a-annotation-based-approach-component)
   - [Static Factory Method](#b-static-factory-method-configuration--bean)
4. [Bean Lifecycle](#bean-lifecycle)
5. [Bean Lifecycle Hooks](#bean-lifecycle-hooks)
6. [Bean Scopes](#bean-scopes)
7. [Commit Timeline](#commit-timeline)
8. [Key Takeaways](#key-takeaways)

---

## What is a Bean?

A **Bean** is an object that is instantiated, assembled, and managed by the **Spring IoC (Inversion of Control) Container**. Instead of manually creating objects using the `new` keyword, Spring handles the entire object lifecycle for us.

**Why use Beans?**
- ✅ Spring automatically manages object creation and destruction
- ✅ Built-in dependency injection reduces boilerplate code
- ✅ Better memory management and resource optimization
- ✅ Easier unit testing with mock objects
- ✅ Promotes loose coupling and clean architecture

---

## XML vs Annotation-Based Configuration

### Before: XML Configuration ❌

In traditional Spring applications, beans were defined in XML files:

```xml
<!-- applicationContext.xml -->
<beans>
    <bean id="demo" class="com.example.demo" scope="singleton"/>
</beans>
```

**Drawbacks:**
- Verbose and error-prone
- No compile-time type checking
- Difficult to maintain for large projects
- Separation between code and configuration

### Current: Annotation-Based Configuration ✅

Modern Spring uses Java annotations for configuration:

```java
@Component
public class demo {
    // Bean logic
}
```

**Advantages:**
- ✅ Less boilerplate code
- ✅ Type-safe and compile-time checked
- ✅ Configuration lives alongside code
- ✅ Easier refactoring and IDE support

---

## Ways to Define Beans

### A. Annotation-Based Approach (@Component)

**Core Concept:**

In Spring, everything in Java can be treated as a bean using annotations like `@Component`, `@Service`, `@Repository`, or `@Controller`.

**How it works:**
- Mark a class with `@Component`
- Use `@Autowired` to inject dependencies
- Spring automatically creates and manages instances
- No need for the `new` keyword

**Why this matters:**

For applications serving millions of users, creating objects repeatedly causes:
- **Memory overhead:** Each user request creates duplicate objects
- **Performance degradation:** Excessive garbage collection
- **Resource wastage:** Unused objects occupy heap space

Spring solves this by:
- Creating beans **only when needed** (lazy initialization)
- Reusing singleton instances across requests
- Efficiently managing object lifecycle

**📝 Implementation (Commit: [added autowired and component annotation](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/6292a4232385608538c7744de132568cc28fd1e3))**

```java
// demo.java
@Component  // Marks this class as a Spring-managed bean
public class demo {
    
    void display() {
        System.out.println("Hello world!");
    }
}
```

```java
// SpringFrameworkApplication.java
@SpringBootApplication
public class SpringFrameworkApplication implements CommandLineRunner {

    @Autowired  // Spring automatically injects the bean
    demo d1;

    @Autowired
    demo d2;

    @Override
    public void run(String... args) throws Exception {
        d1.display();  // No need to create object manually
        d2.display();
    }
}
```

**Key Learning:** We don't have to use the `new` keyword anymore - Spring handles object creation for us!

---

### B. Static Factory Method (@Configuration + @Bean)

**Core Concept:**
- `@Configuration`: Marks a class as a source of bean definitions (replaces XML config)
- `@Bean`: Explicitly defines a bean creation method (replaces `<bean>` tag)

**How it works:**
- Developer creates the object **explicitly** in the `@Bean` method
- Spring handles **dependency injection** and **lifecycle management**
- Provides more control over bean instantiation logic

**📝 Implementation (Commit: [explicit bean declaration](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/a123d6f0fcafe1748b13fbf09b03af883c02add5))**

```java
// configClass.java
@Configuration
public class configClass {
    
    @Bean
    demo getDemo() {
        return new demo();  // Developer explicitly creates the object
    }
}
```

**Key Difference:**
- Developer controls **object creation**
- Spring controls **dependency injection**
- Useful when you need custom initialization logic

---

## Bean Lifecycle

Spring manages beans through the following lifecycle phases:

```
1. Bean Instantiation
         ↓
2. Dependency Injection
         ↓
3. Initialization (@PostConstruct)
         ↓
4. Bean Ready to Use
         ↓
5. Bean Destruction (@PreDestroy)
```

**Detailed Steps:**

1. **Bean Creation** - Spring instantiates the bean using constructor
2. **Dependency Injection** - Spring injects required dependencies via `@Autowired`
3. **Initialization** - `@PostConstruct` method executes (custom setup logic)
4. **Bean Ready to Use** - Bean is available for application use
5. **Bean Destruction** - `@PreDestroy` method executes when application shuts down

---

## Bean Lifecycle Hooks

**📝 Implementation (Commit: [used bean lifecycle hooks](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/8db9ac993556d06b1aa7ef7ba5ba2d359dbbbd34))**

```java
// demo.java
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class demo {
    
    void display() {
        System.out.println("Hello world!");
    }

    @PostConstruct
    void callmebeforedemoiscreated() {
        System.out.println("This is called before creating this bean demo");
    }

    @PreDestroy
    void callmeafterdemoisdestroyed() {
        System.out.println("This is called before destroying this bean demo");
    }
}
```

### @PostConstruct
- Executes **after** dependency injection is complete
- Used for initialization logic (e.g., database connections, loading configuration)
- Called only **once** during bean creation

### @PreDestroy
- Executes **before** the bean is destroyed
- Used for cleanup operations (e.g., closing connections, releasing resources)
- Called when application context shuts down

**Important:** When you stop the application, all `@PreDestroy` methods are automatically called to clean up resources.

---

## Bean Scopes

Spring provides different scopes to control bean lifecycle and instance creation:

| Scope | Description | Use Case |
|-------|-------------|----------|
| **Singleton** (default) | One instance per Spring container | Stateless services, shared resources |
| **Prototype** | New instance every time bean is requested | Stateful objects, user-specific data |
| **Request** | One instance per HTTP request | Web applications, request-specific data |
| **Session** | One instance per HTTP session | User session data |
| **WebSocket** | One instance per WebSocket session | Real-time communication |

---

### Singleton Scope (Default)

**📝 Implementation (Commit: [singleton scope](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/3e5339e9c8d03af47dada020c0afece1c156200a))**

```java
@Configuration
public class configClass {
    
    @Bean
    // @Scope("singleton") - Default, so not needed
    demo getDemo() {
        return new demo();
    }
}
```

**Testing Singleton Scope:**

```java
@Override
public void run(String... args) throws Exception {
    d1.display();
    d2.display();
    
    // Same hashcode proves same instance
    System.out.println(d1.hashCode()); // e.g., 123456
    System.out.println(d2.hashCode()); // e.g., 123456 (same!)
}
```

**Key Point:** Both `d1` and `d2` reference the **same object** in memory.

---

### Prototype Scope

**📝 Implementation (Commit: [prototype scope](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/e109cf7ffc7a663d28414c991ee98f7c095be1dc))**

```java
@Configuration
public class configClass {
    
    @Bean
    @Scope("prototype") // New instance each time
    demo getDemo() {
        return new demo();
    }
}
```

**Testing Prototype Scope:**

```java
@Override
public void run(String... args) throws Exception {
    d1.display();
    d2.display();
    
    // Different hashcodes prove different instances
    System.out.println(d1.hashCode()); // e.g., 123456
    System.out.println(d2.hashCode()); // e.g., 789012 (different!)
}
```

**Key Point:** Each `@Autowired` injection creates a **new instance**.
