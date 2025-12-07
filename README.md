# Spring Framework Learning Journey

This repository documents my hands-on learning experience with Spring Framework and Spring Boot. Each section includes practical implementations with corresponding commit links.

---

## Spring Boot vs Spring Framework

### Overview
Spring Boot is built on top of the Spring Framework and provides additional features that simplify application development and deployment. It eliminates much of the boilerplate configuration required in traditional Spring Framework applications.

---

## 1. Starter Dependencies

Spring Boot provides **starter dependencies** that bundle all necessary libraries and configurations for specific functionalities.

### Key Benefits:
- **Pre-configured Setup**: All required code and configurations are already set up
- **No Building from Scratch**: Developers don't have to manually configure each dependency
- **Automatic Compatibility**: Spring Boot manages version compatibility between dependencies

### Example - `pom.xml`:
```xml
<dependencies>
    <!-- Web MVC Starter: Includes Spring MVC, Tomcat, Jackson, Validation -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webmvc</artifactId>
    </dependency>

    <!-- Test Starter: Includes JUnit, Mockito, Spring Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webmvc-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

**What You Get:**
- Instead of adding 10+ individual dependencies, one starter provides everything needed
- Automatic version management through `spring-boot-starter-parent`
- Compatible library versions guaranteed

---

## 2. Auto Configuration

Spring Boot's **auto-configuration** automatically configures your application based on the dependencies present in the classpath.

### How It Works:
- Spring Boot has an **auto-configuration file** where dependencies are initialized automatically
- When you add a starter dependency, Spring Boot detects it and configures the beans automatically
- No need for extensive XML or Java configuration files

### Example:
```java
@SpringBootApplication  // Includes @EnableAutoConfiguration
public class SpringFrameworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringFrameworkApplication.class, args);
    }
}
```

**What Happens Behind the Scenes:**
- `@EnableAutoConfiguration` scans classpath for libraries
- Automatically configures DataSource if database dependency is found
- Configures DispatcherServlet for web applications
- Sets up default message converters (JSON, XML)
- Configures embedded server (Tomcat by default)

### Benefits:
- **Zero Configuration**: Works out of the box
- **Convention over Configuration**: Sensible defaults provided
- **Customizable**: Can override auto-configuration when needed

---

## 3. Externalized Configuration

Spring Boot supports **externalized configuration**, allowing you to configure application behavior without changing code.

### Configuration Hierarchy:
Spring Boot loads configuration from **14-17 different property sources**, with the following priority (highest to lowest):

1. Command-line arguments
2. Java System properties
3. OS environment variables
4. `application.properties` or `application.yml` (outside the jar)
5. `application.properties` or `application.yml` (inside the jar)
6. `@PropertySource` annotations
7. Default properties

### Application Properties File:
```properties
# Application name
spring.application.name=Spring-Framework

# Custom server port (default is 8080)
server.port=9090

# Custom environment variable
deploy.env=production
```

### How It Works:
- **Application Context** loads all property sources at startup
- Properties can be accessed using `@Value` or `@ConfigurationProperties`
- Easy to override for different environments (dev, test, prod)

### Example Usage:
```java
@Component
@ConditionalOnProperty(name = "deploy.env", havingValue = "production")
public class prodDb implements specificationDB {
    public String getData() {
        return "prod DB!";
    }
}
```

### Real-World Scenario:
When a company needs to build a new microservice:
1. Copy the previous project's configuration file
2. Change environment variables as per the new service requirements
3. **Done!** - No code changes needed, just configuration

**Commit:** [Updated application properties](https://github.com/Shreyash2246/Spring-Framework-Practice/commit/a6ed7af1485f00a177cc934e9a53629c38e3836d)

---

## 4. Embedded Tomcat & Jetty Server

Spring Boot includes **embedded servers** (Tomcat, Jetty, Undertow), eliminating the need for external server setup.

### Traditional Spring Framework:
- Deploy WAR file to external Tomcat server
- Manage server installation and configuration separately
- Complex deployment process

### Spring Boot Approach:
- **Embedded Tomcat** included by default
- Application runs as a standalone JAR file
- Server starts automatically with the application

### Example:
```xml
<!-- Tomcat is included in spring-boot-starter-webmvc -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>
```

### Running the Application:
```bash
# Traditional way - requires external server
# Deploy WAR to Tomcat/Jetty/WebLogic

# Spring Boot way - self-contained
java -jar spring-framework-0.0.1-SNAPSHOT.jar
```

### Benefits:
- **No External Server Setup**: Server is part of the application
- **Consistent Environment**: Same server in dev and production
- **Easy Deployment**: Single JAR file deployment
- **Quick Startup**: Faster development and testing cycles

### Server Configuration:
```properties
# Change default port
server.port=9090

# Context path
server.servlet.context-path=/api

# Session timeout
server.servlet.session.timeout=30m
```

---

## 5. Built-in Metrics and Health Checks

Spring Boot provides **production-ready features** through the **Actuator** dependency for monitoring and managing applications.

### Spring Boot Actuator:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### What You Get:
- **Health Checks**: `/actuator/health` - Application health status
- **Metrics**: `/actuator/metrics` - Memory, CPU, request counts
- **Info**: `/actuator/info` - Application information
- **Environment**: `/actuator/env` - Configuration properties
- **Beans**: `/actuator/beans` - All registered Spring beans

### Key Benefits:
- **No Version Management**: Spring Boot handles version compatibility
- **No Manual Setup**: Everything is auto-configured
- **Production Ready**: Industry-standard monitoring out of the box
- **Easy Integration**: Works with monitoring tools (Prometheus, Grafana)

### Example Health Check Response:
```json
{
  "status": "UP",
  "components": {
    "diskSpace": {
      "status": "UP",
      "details": {
        "total": 500000000000,
        "free": 250000000000
      }
    },
    "db": {
      "status": "UP"
    }
  }
}
```

### Configuration:
```properties
# Enable all actuator endpoints
management.endpoints.web.exposure.include=*

# Custom health check endpoint
management.endpoint.health.show-details=always
```

---

## Developer Benefits: Focus on Business Logic

All these Spring Boot features enable developers to:

### 1. Skip Boilerplate Configuration
- No manual dependency wiring
- No XML configuration files
- No server setup and deployment hassles

### 2. Build Microservices Faster
- Starter dependencies provide everything needed
- Quick project scaffolding
- Easy to create multiple microservices

### 3. Rapid Development Cycle
- Embedded server for quick testing
- Auto-restart with Spring DevTools
- Externalized config for easy environment switching

### 4. Production-Ready from Day One
- Built-in health checks and metrics
- Logging and monitoring configured
- Security features available through starters

### Real-World Example:
When building a new microservice for a company:

**Traditional Spring Framework:**
- Set up Maven dependencies (20+ individual libraries)
- Configure DispatcherServlet, ViewResolver
- Set up Tomcat server
- Configure logging, metrics manually
- Write health check endpoints
- **Time: Several days**

**Spring Boot:**
1. Add `spring-boot-starter-webmvc` dependency
2. Copy previous microservice's `application.properties`
3. Update environment variables for new service
4. Write business logic
5. **Done! Time: Few hours**

---

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/Spring_Framework/Practice/Spring_Framework/
│   │       ├── SpringFrameworkApplication.java
│   │       └── dbExample/
│   │           ├── specificationDB.java
│   │           ├── devDb.java
│   │           ├── prodDb.java
│   │           └── dbService.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
```

---
