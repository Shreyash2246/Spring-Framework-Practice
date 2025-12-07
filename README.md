# Spring Framework - Maven Build Tool

## 1.7 Maven

This repository documents my learning journey with Spring Framework, focusing on Maven as a build tool for Java projects.

---

## What is Maven?

**Maven** is a powerful build tool used to create and manage Java projects. It is crucial for both:
- **Development** - Managing dependencies, compiling code, running tests
- **Deployment** - Building artifacts (JAR/WAR files), deploying to servers

Maven automates the build process and handles project dependencies, making Java development more efficient and consistent.

---

## Maven As a Chef

Think of Maven as a **chef in a kitchen**:

- **Recipe (pom.xml)**: The chef follows a recipe that lists all ingredients and cooking steps
- **Ingredients (Dependencies)**: Maven downloads all required libraries from repositories
- **Cooking Process (Build Lifecycle)**: Maven follows standard steps to prepare your application
- **Final Dish (JAR/WAR file)**: A ready-to-serve application that can run anywhere

Just like a chef transforms raw ingredients into a finished meal, Maven transforms your source code and dependencies into a deployable application.

---

## Project and Dependency Management

Maven manages your project through the `pom.xml` file (Project Object Model).

### Example pom.xml
```xml
<project>
    <groupId>com.Spring_Framework.Practice</groupId>
    <artifactId>Spring_Framework</artifactId>
    <version>1.0.0</version>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>3.2.0</version>
        </dependency>
    </dependencies>
</project>
```

### How Dependency Management Works

1. **You declare dependencies** in `pom.xml`
2. **Maven downloads them** from Maven Central or other repositories
3. **Maven resolves transitive dependencies** (dependencies of your dependencies)
4. **All JARs are added to your classpath** automatically

```bash
# Maven downloads dependencies to your local repository
~/.m2/repository/
```

---

## Build Automation

Maven provides a **default build lifecycle** that automates the entire build process.

### Default Maven Lifecycle

Maven follows these phases in order:

1. **validate** - Validates the project structure
2. **compile** - Compiles source code
3. **test** - Runs unit tests
4. **package** - Creates JAR/WAR files
5. **verify** - Runs integration tests
6. **install** - Installs to local repository
7. **deploy** - Deploys to remote repository

### Creating JAR Files

Maven packages your application into a JAR file that can run on **any platform** with Java installed.

```bash
# Build the project and create JAR
mvn package

# Output:
# target/Spring_Framework-1.0.0.jar
```

This JAR file is portable - you can run it on:
- Windows
- Linux
- macOS
- Any system with Java Runtime Environment (JRE)

```bash
# Run the JAR file
java -jar target/Spring_Framework-1.0.0.jar
```

---

## Using Maven Wrapper (mvnw)

The **Maven Wrapper** (`mvnw`) ensures everyone uses the same Maven version without installing Maven globally.

### Why Use mvnw?

- **Consistency** - Team members use the same Maven version
- **No installation required** - Maven is downloaded automatically
- **Project-specific** - Each project can use different Maven versions

### Common mvnw Commands

```bash
# Compile the project
./mvnw compile

# Run tests
./mvnw test

# Package into JAR
./mvnw package

# Clean previous builds
./mvnw clean

# Clean and package
./mvnw clean package

# Run the Spring Boot application
./mvnw spring-boot:run
```

### Windows Commands
```cmd
# On Windows, use mvnw.cmd
mvnw.cmd compile
mvnw.cmd package
mvnw.cmd spring-boot:run
```

---

## Advanced Maven Capabilities

### Building Docker Images

Maven can be configured to build Docker images directly.

```xml
<plugin>
    <groupId>com.spotify</groupId>
    <artifactId>dockerfile-maven-plugin</artifactId>
    <configuration>
        <repository>myapp</repository>
        <tag>${project.version}</tag>
    </configuration>
</plugin>
```

```bash
# Build Docker image using Maven
mvn dockerfile:build
```

### Deploying to Maven Repository

Maven can deploy your artifacts to repositories for sharing with other teams.

#### Maven Central Repository
- **Public repository** where open-source libraries are hosted
- Used for downloading dependencies
- Requires verification to publish

#### Remote/Private Repository
- **Company-specific** repository (Nexus, Artifactory)
- Stores internal libraries and artifacts
- Controlled access

```xml
<distributionManagement>
    <repository>
        <id>central</id>
        <url>https://repo.maven.apache.org/maven2</url>
    </repository>
    <snapshotRepository>
        <id>snapshots</id>
        <url>https://your-company-repo/snapshots</url>
    </snapshotRepository>
</distributionManagement>
```

```bash
# Deploy to remote repository
mvn deploy
```

---

## Summary

Maven simplifies Java project management by:

- **Managing dependencies** automatically from repositories
- **Automating builds** through a standard lifecycle
- **Creating portable JAR files** that run on any platform with Java
- **Providing mvnw** for consistent builds across teams
- **Building Docker images** for containerized deployments
- **Deploying artifacts** to Maven Central or private repositories

Maven is an essential tool that allows developers to focus on writing code while it handles the complexity of building and deploying applications.

---
