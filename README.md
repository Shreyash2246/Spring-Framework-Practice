# Spring Framework - AutoConfiguration & Internal Workflow

## 1.6 AutoConfiguration & Spring Boot Internal Workflow

This repository documents my learning journey with Spring Framework, focusing on AutoConfiguration and how Spring Boot works internally.

---

## Understanding pom.xml

The `pom.xml` file is the heart of a Maven-based Spring Boot project. Here's what it does:

- **Dependency Management**: Defines where we can add project dependencies
- **Maven Integration**: Enables Maven to work properly with the project
- **Classpath Loading**: Loads all dependencies into the project's classpath
- **AutoConfiguration Support**: Helps Spring Boot autoconfiguration by making dependencies discoverable

### The `<parent>` Tag

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.x.x</version>
</parent>
```

The `<parent>` section:
- Manages all Spring Boot dependencies
- Maintains version compatibility automatically
- Ensures all libraries work together without conflicts

---

## What is AutoConfiguration?

**AutoConfiguration** is Spring Boot's intelligent way of automatically configuring your application based on:
1. **Classpath scanning** - What dependencies are available
2. **Application-specific configuration** - Your custom settings

### Key Benefits

- Allows developers to **focus on business logic** instead of boilerplate configuration
- Provides sensible defaults out of the box

### Example Scenario

Suppose you don't provide any configuration for a Tomcat server. AutoConfiguration will:
- Detect that you have web dependencies in the classpath
- Automatically configure an embedded Tomcat server
- Use default settings (port 8080, context path "/", etc.)

---

## How AutoConfiguration Works

AutoConfiguration follows a three-step process:

### 1. Classpath Scanning
Spring Boot scans the classpath to detect available libraries and dependencies.

```java
// When Spring Boot finds this on the classpath:
// spring-boot-starter-web
// It automatically configures:
// - Embedded Tomcat
// - DispatcherServlet
// - Default error handling
```

### 2. Configuration Classes
Spring Boot uses specialized `@Configuration` classes to set up beans.

```java
@Configuration
@ConditionalOnClass(DataSource.class)
public class DataSourceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() {
        // Auto-configure DataSource
        return new HikariDataSource();
    }
}
```

### 3. Conditional Beans
Beans are created only when certain conditions are met.

```java
@Bean
@ConditionalOnProperty(name = "app.feature.enabled", havingValue = "true")
public FeatureService featureService() {
    return new FeatureService();
}
```

---

## Core Features of AutoConfiguration

### 1. @PropertySources Auto Registration

Spring Boot automatically registers property sources in a specific order (17 different sources total). Common ones include:

1. Command line arguments
2. `application.properties` / `application.yml`
3. Environment variables
4. System properties

```properties
# application.properties
server.port=9090
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
```

### 2. META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports

This file contains the list of autoconfiguration classes that Spring Boot should load.

```
# Example from spring-boot-autoconfigure JAR
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
```

### 3. Enhanced Conditional Support

Spring Boot provides powerful conditional annotations:

#### @ConditionalOnBean
```java
@Bean
@ConditionalOnBean(DataSource.class)
public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
}
```

#### @ConditionalOnClass
```java
@Configuration
@ConditionalOnClass(name = "org.hibernate.SessionFactory")
public class HibernateConfiguration {
    // Only loaded if Hibernate is on classpath
}
```

#### @ConditionalOnProperty
```java
@Bean
@ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager();
}
```

---

## Key Insight

> **Spring Boot is essentially a collection of AutoConfiguration classes that create beans when certain conditions are met.**

This means you get a fully configured application without writing extensive XML or Java configuration files.

---

## Spring Boot Internal Flow

Understanding how Spring Boot starts up helps you debug issues and customize behavior.

### 1. Initialization
```java
@SpringBootApplication
public class SpringFrameworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringFrameworkApplication.class, args);
    }
}
```

The `SpringApplication.run()` method kicks off the entire process.

### 2. Spring Application Context Creation

Spring Boot creates an appropriate `ApplicationContext` based on the classpath:
- `AnnotationConfigServletWebServerApplicationContext` for web applications
- `AnnotationConfigApplicationContext` for non-web applications

### 3. AutoConfiguration Start

Spring Boot:
- Loads `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`
- Evaluates conditional annotations
- Creates beans that match the conditions

### 4. Externalized Configuration

Property sources are loaded in order:
1. Default properties
2. `@PropertySource` annotations
3. Config data files (`application.properties`)
4. Command line arguments (highest priority)

### 5. Embedded Web Server Initialization

If it's a web application:
```java
// Spring Boot automatically configures:
TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
factory.setPort(8080); // from properties
WebServer webServer = factory.getWebServer();
webServer.start();
```

### 6. Application Startup

- All `@Component`, `@Service`, `@Repository`, and `@Controller` beans are created
- Dependency injection occurs
- `@PostConstruct` methods are called

### 7. Application is Ready

- `ApplicationReadyEvent` is published
- Application is now ready to accept requests
- CommandLineRunners and ApplicationRunners execute

```java
@Component
public class StartupRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("Application is ready!");
    }
}
```

---

## Summary

Spring Boot's AutoConfiguration eliminates boilerplate code by:
- Scanning the classpath for dependencies
- Applying sensible default configurations
- Using conditional logic to create beans only when needed
- Loading properties from multiple sources with a defined priority

This allows developers to get started quickly and focus on writing business logic rather than infrastructure code.

---
