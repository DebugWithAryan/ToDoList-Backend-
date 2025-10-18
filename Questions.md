# Interview/Exam Questions - ToDoList Backend Project

## Spring Boot Concepts

### Q1: What is Spring Boot and why is it used?
**Answer:** Spring Boot is a framework built on top of the Spring Framework that simplifies the development of production-ready applications. It provides auto-configuration, embedded servers, and starter dependencies. In this project, Spring Boot eliminates the need for XML configuration and provides an embedded Tomcat server to run the application.

### Q2: What is the purpose of `@SpringBootApplication` annotation?
**Answer:** The `@SpringBootApplication` annotation is a convenience annotation that combines three annotations:
- `@Configuration` - Marks the class as a source of bean definitions
- `@EnableAutoConfiguration` - Enables Spring Boot's auto-configuration mechanism
- `@ComponentScan` - Enables component scanning to find Spring components

It is used in `ToDoListBackendApplication.java` to bootstrap the Spring Boot application.

### Q3: Explain the `@RestController` annotation.
**Answer:** `@RestController` is a specialized version of `@Controller` that combines `@Controller` and `@ResponseBody`. It indicates that the class handles HTTP requests and automatically serializes return values to JSON/XML. In `TodoController.java`, it marks the class as a REST API controller.

### Q4: What is `@RequestMapping` and how is it used?
**Answer:** `@RequestMapping` maps HTTP requests to handler methods. In `TodoController.java`, `@RequestMapping("/api/todos")` maps all requests starting with `/api/todos` to this controller. It can specify HTTP methods, headers, and content types.

### Q5: What is the difference between `@GetMapping`, `@PostMapping`, `@PutMapping`, `@PatchMapping`, and `@DeleteMapping`?
**Answer:** These are specialized versions of `@RequestMapping` for specific HTTP methods:
- `@GetMapping` - HTTP GET (retrieve data)
- `@PostMapping` - HTTP POST (create data)
- `@PutMapping` - HTTP PUT (full update)
- `@PatchMapping` - HTTP PATCH (partial update)
- `@DeleteMapping` - HTTP DELETE (remove data)

The project uses all these annotations in `TodoController.java`.

## Spring Data JPA Concepts

### Q6: What is JPA and what role does it play in this project?
**Answer:** JPA (Java Persistence API) is a specification for object-relational mapping in Java. It allows mapping Java objects to database tables. In this project, JPA is used through Spring Data JPA to manage the `ToDo` entity and automatically handle database operations without writing SQL queries.

### Q7: Explain the `@Entity` annotation.
**Answer:** `@Entity` marks a class as a JPA entity, meaning it will be mapped to a database table. In `ToDo.java`, this annotation indicates that the `ToDo` class corresponds to the `todos` table in the database.

### Q8: What is the purpose of `@Table(name="todos")`?
**Answer:** `@Table` specifies the database table name for the entity. In `ToDo.java`, it explicitly sets the table name to "todos". Without this annotation, JPA would use the class name as the table name.

### Q9: Explain `@Id` and `@GeneratedValue` annotations.
**Answer:** 
- `@Id` marks a field as the primary key of the entity
- `@GeneratedValue(strategy = GenerationType.IDENTITY)` specifies that the database will auto-generate the ID value using an auto-increment column

In `ToDo.java`, the `id` field uses both annotations.

### Q10: What do `@Column` annotations do?
**Answer:** `@Column` specifies the mapping of a field to a database column. It can define properties like:
- `nullable = false` - NOT NULL constraint
- `length = 10000` - Column length
- `name = "created_at"` - Column name different from field name

Used in `ToDo.java` for various fields.

### Q11: What is the purpose of `@PrePersist` and `@PreUpdate`?
**Answer:** These are JPA lifecycle callback annotations:
- `@PrePersist` - Executes before an entity is saved to the database for the first time
- `@PreUpdate` - Executes before an entity is updated in the database

In `ToDo.java`, `onCreate()` sets timestamps when creating a todo, and `onUpdate()` updates the timestamp when modifying a todo.

### Q12: What is `JpaRepository` and what does it provide?
**Answer:** `JpaRepository` is a Spring Data interface that provides built-in CRUD operations without requiring implementation. It provides methods like `findAll()`, `findById()`, `save()`, `delete()`, etc. In `TodoRepository.java`, extending `JpaRepository<ToDo, Long>` gives access to these methods automatically.

### Q13: Explain custom query methods in Spring Data JPA.
**Answer:** Spring Data JPA can derive queries from method names. In `TodoRepository.java`:
- `findByCompleted(Boolean completed)` - Finds todos by completion status
- `findByTitleContainingIgnoreCase(String title)` - Searches titles case-insensitively

Spring Data JPA automatically generates SQL queries from these method names.

## Dependency Injection and Architecture

### Q14: What is Dependency Injection and how is it implemented in this project?
**Answer:** Dependency Injection (DI) is a design pattern where objects receive their dependencies from external sources rather than creating them. In this project, `@Autowired` injects dependencies:
- `TodoRepository` is injected into `TodoService`
- `TodoService` is injected into `TodoController`

Spring manages the lifecycle and wiring of these components.

### Q15: What is the purpose of the `@Service` annotation?
**Answer:** `@Service` is a specialization of `@Component` that marks a class as a service layer component. It indicates that the class contains business logic. In `TodoService.java`, it marks the class as a service that performs business operations on todos.

### Q16: What is the purpose of the `@Repository` annotation?
**Answer:** `@Repository` is a specialization of `@Component` that marks a class as a data access layer component. It enables exception translation from database-specific exceptions to Spring's DataAccessException. In `TodoRepository.java`, it marks the interface as a repository.

### Q17: Explain the layered architecture used in this project.
**Answer:** The project follows a three-layer architecture:
- **Controller Layer** (`TodoController`) - Handles HTTP requests/responses
- **Service Layer** (`TodoService`) - Contains business logic
- **Repository Layer** (`TodoRepository`) - Handles database operations

This separation provides modularity, testability, and maintainability.

## HTTP and REST Concepts

### Q18: What is REST and what makes an API RESTful?
**Answer:** REST (Representational State Transfer) is an architectural style for web services. A RESTful API:
- Uses HTTP methods appropriately (GET, POST, PUT, PATCH, DELETE)
- Is stateless
- Has resource-based URLs
- Returns standard HTTP status codes

This project implements a RESTful API for todo resources.

### Q19: What is `ResponseEntity` and why is it used?
**Answer:** `ResponseEntity` represents the entire HTTP response including status code, headers, and body. In `TodoController.java`, it's used to return appropriate HTTP status codes:
- `200 OK` for successful retrieval
- `201 CREATED` for successful creation
- `204 NO CONTENT` for successful deletion
- `404 NOT FOUND` for missing resources

### Q20: What is `@RequestBody` and when is it used?
**Answer:** `@RequestBody` binds the HTTP request body to a method parameter. It deserializes JSON/XML to a Java object. In `TodoController.java`, it's used in POST and PUT methods to receive todo data from the client.

### Q21: What is `@PathVariable` and how does it work?
**Answer:** `@PathVariable` extracts values from the URI path. In `TodoController.java`, `@PathVariable Long id` in methods like `getTodoById(@PathVariable Long id)` extracts the ID from URLs like `/api/todos/5`.

### Q22: What is `@RequestParam` and how is it different from `@PathVariable`?
**Answer:** `@RequestParam` extracts query parameters from the URL. In `TodoController.java`:
```java
@RequestParam(required = false) Boolean completed
@RequestParam(required = false) String search
```
Extracts parameters from URLs like `/api/todos?completed=true&search=buy`

Difference: `@PathVariable` is part of the path (`/todos/5`), `@RequestParam` is in query string (`/todos?id=5`).

### Q23: What is CORS and why is it needed?
**Answer:** CORS (Cross-Origin Resource Sharing) is a security mechanism that controls which domains can access your API. Without it, browsers block requests from different domains. In `TodoController.java`, `@CrossOrigin(origins = "*")` allows all domains to access the API, enabling frontend applications from any origin to connect.

## Database and Configuration

### Q24: What is HikariCP and why is it used?
**Answer:** HikariCP is a high-performance JDBC connection pool. It manages database connections efficiently by reusing them instead of creating new connections for each request. In `DatabaseConfig.java`, HikariCP is configured with:
- Connection pooling parameters
- Connection lifecycle management
- Better performance and resource utilization

### Q25: Explain the database configuration in `DatabaseConfig.java`.
**Answer:** The `DatabaseConfig` class:
1. Reads `DATABASE_URL` from environment variables
2. Parses the PostgreSQL URL to extract host, port, username, password
3. Constructs a JDBC URL
4. Configures HikariCP with connection pool settings
5. Returns a `DataSource` bean used by Spring Data JPA

### Q26: What is the purpose of the `@Configuration` annotation?
**Answer:** `@Configuration` indicates that a class contains `@Bean` definitions. Spring processes this class and registers the beans in the application context. In `DatabaseConfig.java`, it marks the class as a configuration class that provides the `DataSource` bean.

### Q27: What is the `@Bean` annotation?
**Answer:** `@Bean` marks a method that produces a bean managed by Spring. The method's return value is registered in the Spring container. In `DatabaseConfig.java`, the `dataSource()` method is annotated with `@Bean` to provide the database connection configuration.

### Q28: What is the purpose of `@Value` annotation?
**Answer:** `@Value` injects property values from configuration files or environment variables. In `DatabaseConfig.java`, `@Value("${DATABASE_URL:}")` injects the `DATABASE_URL` environment variable, with an empty string as default if not found.

### Q29: Explain the HikariCP configuration parameters used in the project.
**Answer:**
- `MaximumPoolSize: 5` - Maximum 5 concurrent connections
- `MinimumIdle: 1` - Keep at least 1 connection ready
- `ConnectionTimeout: 30000` - Wait 30s for connection
- `IdleTimeout: 600000` - Close idle connections after 10 minutes
- `MaxLifetime: 1800000` - Recycle connections after 30 minutes

These settings balance performance and resource usage.

## Optional and Error Handling

### Q30: What is `Optional<T>` and why is it used?
**Answer:** `Optional<T>` is a container that may or may not contain a value, helping avoid `NullPointerException`. In `TodoService.java`, `getTodoById()` returns `Optional<ToDo>` because a todo with the given ID might not exist. The controller uses `.map()` and `.orElse()` to handle both cases gracefully.

### Q31: How does error handling work in the controller?
**Answer:** The controller uses try-catch blocks to handle `RuntimeException` thrown by the service layer. When a todo is not found:
- Service throws `RuntimeException("Todo not found")`
- Controller catches it and returns `404 NOT FOUND` status

This approach separates business logic errors from HTTP responses.

## Docker and Deployment

### Q32: Explain the multi-stage Docker build in the Dockerfile.
**Answer:** The Dockerfile uses two stages:
1. **Build Stage** - Uses `maven:3.9.5-eclipse-temurin-21` to compile the application with Maven
2. **Runtime Stage** - Uses smaller `eclipse-temurin:21-jre-alpine` with only the JRE to run the JAR

This creates a smaller final image by excluding build tools.

### Q33: What is the purpose of `.dockerignore`?
**Answer:** `.dockerignore` specifies files/directories to exclude from the Docker build context. This reduces build time and image size by excluding:
- Build outputs (target/)
- IDE files (.idea/, .vscode/)
- Documentation files
- Git repository

### Q34: Explain the ENTRYPOINT in the Dockerfile.
**Answer:** 
```dockerfile
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dserver.port=${PORT:-8080} -jar app.jar"]
```
- Uses shell to expand environment variables
- `$JAVA_OPTS` - JVM configuration
- `-Dserver.port=${PORT:-8080}` - Uses PORT env var or defaults to 8080
- Runs the Spring Boot JAR file

## Best Practices and Patterns

### Q35: What design pattern is used in the service layer?
**Answer:** The Service Layer pattern encapsulates business logic. `TodoService` acts as a facade between the controller and repository, providing a clean interface for business operations and keeping the controller thin.

### Q36: What is the difference between PUT and PATCH in this project?
**Answer:** 
- **PUT** (`/api/todos/{id}`) - Full update, replaces entire todo (title, description, completed)
- **PATCH** (`/api/todos/{id}/toggle`) - Partial update, only toggles the completion status

This follows REST conventions for update operations.

### Q37: Why use `LocalDateTime` instead of `Date` for timestamps?
**Answer:** `LocalDateTime` (Java 8+) provides:
- Better API for date/time operations
- Immutability (thread-safe)
- No timezone confusion
- More readable code

In `ToDo.java`, `createdAt` and `updatedAt` use `LocalDateTime`.

### Q38: What is the purpose of the default constructor in `ToDo.java`?
**Answer:** JPA requires a no-argument constructor to instantiate entities when loading data from the database. `public ToDo() {}` provides this required constructor.

### Q39: Why is `completed` initialized to `false` in the entity?
**Answer:** `private Boolean completed = false;` ensures new todos are marked as incomplete by default if the client doesn't specify the status. This provides sensible default behavior.

### Q40: What would happen if `DATABASE_URL` is not set?
**Answer:** The `dataSource()` method in `DatabaseConfig.java` checks if `DATABASE_URL` is null or empty and throws `IllegalArgumentException("DATABASE_URL not found in environment variables")`, preventing the application from starting with invalid configuration.
