Project Root Files
pom.xml
The Project Object Model file is Maven's heart - it manages all dependencies, plugins, build configuration, and project metadata. Maven automatically downloads required libraries during builds, handles versioning, and resolves transitive dependencies (dependencies of your dependencies). It contains your Group, Artifact, Version, Java version, Spring Boot version, and all dependency declarations.​
.mvn, mvnw, mvnw.cmd
Maven Wrapper files that allow running Maven commands without having Maven installed globally on your system. The mvnw script is for Unix/Linux, mvnw.cmd is for Windows.​

.gitignore
Specifies which files Git should ignore in version control (like compiled classes, IDE files, logs).​

src/main Folder
src/main/java
Contains all your application's production source code.​

Main Application Class
IntroductionToSpringBootApplication.java - The entry point with @SpringBootApplication annotation and main() method that starts your Spring Boot application.​

Package Structure (Best Practices)
Inside your base package (com.codingshuttle.anuj.week1Introduction), you should organize code by layers:​
• controllers: Handle incoming HTTP requests, define REST API endpoints, process requests and return responses​
• services: Contain business logic, process data, enforce business rules​
• repositories: Handle database operations (CRUD), interact with databases using Spring Data JPA​
• entities/models: Java classes mapping to database tables, define data structure and relationships​
• dto (Data Transfer Objects): Plain Java objects for transferring data between layers​
• config: Configuration classes for customizing application behavior​
• exceptions: Custom exception classes for error handling​

src/main/resources
Contains non-code files:​
• application.properties: Configuration file for database connections, server port (default 8080), logging settings, etc.​
• static: For static resources like CSS, JavaScript, images​
• templates: For HTML templates if using Thymeleaf or similar templating engines​

src/test Folder
Purpose of Test Folder
This is crucial for Test Driven Development (TDD) - a methodology used by big tech companies where you write tests before writing actual code. TDD follows the Red-Green-Refactor cycle: write failing test (Red) → write minimum code to pass (Green) → improve code quality (Refactor).​
Benefits of TDD
• Catches bugs early in development​
• Ensures high code quality and test coverage​
• Makes refactoring safer with comprehensive test suites​
• Leads to better software architecture and cleaner code​
• Facilitates collaboration and maintenance​

src/test/java
Contains test classes mirroring your main code structure. Example: IntroductionToSpringBootApplicationTests.java tests your main application class.​
Spring Boot provides powerful testing annotations:​
• @SpringBootTest: For integration tests
• @WebMvcTest: For testing controllers
• @DataJpaTest: For testing repositories
• @MockBean: For mocking dependencies

src/test/resources
Contains test-specific configuration files like application-test.properties for test database settings.​
Other Folders

.idea
IntelliJ IDEA-specific folder containing IDE settings and configurations (not part of your application).​
target
Generated during Maven build process:​
• classes: Compiled .class files from your source code
• test-classes: Compiled test classes
• generated-sources: Auto-generated code during build
• Contains your final JAR/WAR file after packaging

External Libraries
Shows all Maven dependencies downloaded and available to your project (visible in IDE).
