# ToDoList Backend

A Spring Boot REST API for managing todo items with PostgreSQL database.

---

### 🎓 Preparing for Interviews or Exams?

[![Interview Questions](https://img.shields.io/badge/📚_Interview_Questions-Click_Here-blue?style=for-the-badge)](./QUESTIONS.md)

**Master the concepts behind this project!** We've prepared 40+ comprehensive questions covering Spring Boot, JPA, REST APIs, Docker, and more. Each question includes detailed answers with real examples from this codebase. Perfect for interview preparation! 🚀

---

## Table of Contents
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Database Schema](#database-schema)
- [API Endpoints](#api-endpoints)
- [Docker Deployment](#docker-deployment)

## Technologies Used

- **Java 21** - Programming language
- **Spring Boot 3.5.6** - Application framework
- **Spring Data JPA** - Data persistence
- **PostgreSQL** - Database
- **HikariCP** - Connection pooling
- **Maven 3.9.5** - Build tool
- **Docker** - Containerization

## Project Structure

```
ToDoListBackend/
├── src/
│   └── main/
│       └── java/
│           └── org/aryan/todolistbackend/
│               ├── config/
│               │   └── DatabaseConfig.java          # Database configuration with HikariCP
│               ├── controller/
│               │   └── TodoController.java          # REST API endpoints
│               ├── model/
│               │   └── ToDo.java                    # JPA Entity
│               ├── repo/
│               │   └── TodoRepository.java          # Data access layer
│               ├── service/
│               │   └── TodoService.java             # Business logic
│               └── ToDoListBackendApplication.java  # Main application class
├── Dockerfile                                        # Docker build configuration
├── .dockerignore                                     # Docker ignore file
├── .gitignore                                        # Git ignore file
├── pom.xml                                           # Maven dependencies
└── README.md
```

### Component Descriptions

**DatabaseConfig.java**
- Parses `DATABASE_URL` environment variable
- Configures HikariCP connection pool
- Creates DataSource bean for database connectivity

**TodoController.java**
- REST API controller with CORS enabled (`@CrossOrigin(origins = "*")`)
- Handles HTTP requests for all CRUD operations
- Returns appropriate HTTP status codes

**ToDo.java**
- JPA entity mapped to `todos` table
- Auto-generates timestamps on create and update
- Fields: id, title, description, completed, createdAt, updatedAt

**TodoRepository.java**
- Extends JpaRepository for basic CRUD operations
- Custom query methods: `findByCompleted()` and `findByTitleContainingIgnoreCase()`

**TodoService.java**
- Business logic layer
- Handles all todo operations
- Throws RuntimeException when todo not found

## Prerequisites

Before starting, ensure you have:

- **Java Development Kit (JDK) 21** or higher
- **Maven 3.9.5** or higher
- **PostgreSQL 12+** (or use Docker)
- **Git** for cloning the repository
- **Docker** (optional, for containerized deployment)

### Verify Prerequisites

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check PostgreSQL
psql --version

# Check Docker (optional)
docker --version
```

## Getting Started

### Step 1: Clone the Repository

```bash
# Clone using HTTPS
git clone https://github.com/DebugWithAryan/ToDoList-Backend-.git

# OR clone using SSH
git clone git@github.com:DebugWithAryan/ToDoList-Backend-.git

# Navigate to project directory
cd ToDoList-Backend-
```

### Step 2: Set Up PostgreSQL Database

#### Option A: Local PostgreSQL Installation

1. Install PostgreSQL on your system
2. Start PostgreSQL service
3. Create database:

```bash
# Connect to PostgreSQL
psql -U postgres

# Create database
CREATE DATABASE todolist;

# Exit
\q
```

#### Option B: Using Docker

```bash
# Run PostgreSQL container
docker run --name postgres-db \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=todolist \
  -p 5432:5432 \
  -d postgres:15-alpine
```

### Step 3: Configure Environment Variables

Set the `DATABASE_URL` environment variable:

#### On Linux/Mac:
```bash
export DATABASE_URL="postgresql://postgres:postgres@localhost:5432/todolist"
```

#### On Windows (Command Prompt):
```cmd
set DATABASE_URL=postgresql://postgres:postgres@localhost:5432/todolist
```

#### On Windows (PowerShell):
```powershell
$env:DATABASE_URL="postgresql://postgres:postgres@localhost:5432/todolist"
```

**URL Format:**
```
postgresql://[username]:[password]@[host]:[port]/[database]
```

### Step 4: Build the Project

Using Maven Wrapper (recommended):

```bash
# On Linux/Mac
chmod +x mvnw
./mvnw clean package -DskipTests

# On Windows
mvnw.cmd clean package -DskipTests
```

Or using Maven directly:

```bash
mvn clean package -DskipTests
```

This will:
- Download all dependencies from `pom.xml`
- Compile the source code
- Create a JAR file in `target/` directory

### Step 5: Run the Application

#### Method 1: Using Maven
```bash
# Linux/Mac
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

#### Method 2: Using JAR file
```bash
java -jar target/ToDoListBackend-0.0.1-SNAPSHOT.jar
```

#### Method 3: With Custom Port
```bash
java -Dserver.port=9090 -jar target/ToDoListBackend-0.0.1-SNAPSHOT.jar
```

### Step 6: Verify Application is Running

The application will start on `http://localhost:8080`

Test the API:
```bash
# Using curl
curl http://localhost:8080/api/todos

# Using browser
Open http://localhost:8080/api/todos
```

You should see an empty array `[]` if no todos exist.

## Live Deployment

The application is deployed and accessible at:

**Base URL:** `https://todoapp-en1q.onrender.com`

**API Endpoint:** `https://todoapp-en1q.onrender.com/api/todos`

You can test the live API directly without any local setup:

```bash
# Test live API
curl https://todoapp-en1q.onrender.com/api/todos
```

Or open in browser: [https://todoapp-en1q.onrender.com/api/todos](https://todoapp-en1q.onrender.com/api/todos)

## Configuration

### Environment Variables

| Variable       | Description                           | Required | Default |
|----------------|---------------------------------------|----------|---------|
| DATABASE_URL   | PostgreSQL connection string          | Yes      | -       |
| PORT           | Application server port               | No       | 8080    |
| JAVA_OPTS      | JVM configuration options             | No       | -Xmx512m -Xms256m |

### HikariCP Connection Pool Settings

Configured in `DatabaseConfig.java`:

```java
Maximum Pool Size: 5        // Max concurrent connections
Minimum Idle: 1             // Min idle connections in pool
Connection Timeout: 30000   // 30 seconds
Idle Timeout: 600000        // 10 minutes
Max Lifetime: 1800000       // 30 minutes
```

These settings optimize database connection management for better performance.

## Database Schema

### `todos` Table

| Column       | Type            | Constraints    | Description                    |
|--------------|-----------------|----------------|--------------------------------|
| id           | BIGINT          | PRIMARY KEY    | Auto-generated ID              |
| title        | VARCHAR(255)    | NOT NULL       | Todo title                     |
| description  | VARCHAR(10000)  | -              | Todo description (optional)    |
| completed    | BOOLEAN         | NOT NULL       | Completion status (default: false) |
| created_at   | TIMESTAMP       | -              | Creation timestamp             |
| updated_at   | TIMESTAMP       | -              | Last update timestamp          |

The table is automatically created by JPA when the application starts.

## API Endpoints

**Local Base URL:** `http://localhost:8080/api/todos`

**Live Deployment:** `https://todoapp-en1q.onrender.com/api/todos`

### 1. Get All Todos

```http
GET /api/todos
```

**Query Parameters:**
- `completed` (Boolean, optional) - Filter by completion status
- `search` (String, optional) - Search by title (case-insensitive)

**Examples:**
```bash
# Get all todos (Local)
curl http://localhost:8080/api/todos

# Get all todos (Live)
curl https://todoapp-en1q.onrender.com/api/todos

# Get only completed todos
curl http://localhost:8080/api/todos?completed=true

# Get only incomplete todos
curl http://localhost:8080/api/todos?completed=false

# Search todos by title
curl http://localhost:8080/api/todos?search=buy
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "title": "Buy groceries",
    "description": "Milk, bread, eggs",
    "completed": false,
    "createdAt": "2025-10-18T10:30:00",
    "updatedAt": "2025-10-18T10:30:00"
  }
]
```

### 2. Get Todo by ID

```http
GET /api/todos/{id}
```

**Example:**
```bash
curl http://localhost:8080/api/todos/1
```

**Response:** 
- `200 OK` - Todo found
- `404 NOT FOUND` - Todo not found

### 3. Create Todo

```http
POST /api/todos
Content-Type: application/json
```

**Request Body:**
```json
{
  "title": "Buy groceries",
  "description": "Milk, bread, eggs"
}
```

**Example:**
```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Buy groceries","description":"Milk, bread, eggs"}'
```

**Response:** `201 CREATED`
```json
{
  "id": 1,
  "title": "Buy groceries",
  "description": "Milk, bread, eggs",
  "completed": false,
  "createdAt": "2025-10-18T10:30:00",
  "updatedAt": "2025-10-18T10:30:00"
}
```

### 4. Update Todo

```http
PUT /api/todos/{id}
Content-Type: application/json
```

**Request Body:**
```json
{
  "title": "Updated title",
  "description": "Updated description",
  "completed": true
}
```

**Example:**
```bash
curl -X PUT http://localhost:8080/api/todos/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated title","description":"Updated description","completed":true}'
```

**Response:** 
- `200 OK` - Todo updated
- `404 NOT FOUND` - Todo not found

### 5. Toggle Completion Status

```http
PATCH /api/todos/{id}/toggle
```

**Example:**
```bash
curl -X PATCH http://localhost:8080/api/todos/1/toggle
```

**Response:** 
- `200 OK` - Status toggled
- `404 NOT FOUND` - Todo not found

### 6. Delete Todo

```http
DELETE /api/todos/{id}
```

**Example:**
```bash
curl -X DELETE http://localhost:8080/api/todos/1
```

**Response:** 
- `204 NO CONTENT` - Todo deleted
- `404 NOT FOUND` - Todo not found

## Docker Deployment

### Understanding the Dockerfile

The project uses a **multi-stage build**:

1. **Build Stage** - Compiles the application
   - Uses `maven:3.9.5-eclipse-temurin-21`
   - Copies Maven wrapper and dependencies
   - Builds the JAR file

2. **Runtime Stage** - Runs the application
   - Uses smaller `eclipse-temurin:21-jre-alpine`
   - Only includes JRE and the JAR file
   - Reduces final image size

### Build Docker Image

```bash
# Build image
docker build -t todolist-backend .

# Check image size
docker images todolist-backend
```

### Run Docker Container

#### Basic Run:
```bash
docker run -d \
  --name todolist-api \
  -p 8080:8080 \
  -e DATABASE_URL="postgresql://postgres:postgres@host.docker.internal:5432/todolist" \
  todolist-backend
```

#### With Custom Port:
```bash
docker run -d \
  --name todolist-api \
  -p 9090:9090 \
  -e PORT=9090 \
  -e DATABASE_URL="postgresql://postgres:postgres@host.docker.internal:5432/todolist" \
  todolist-backend
```

#### With Custom JVM Options:
```bash
docker run -d \
  --name todolist-api \
  -p 8080:8080 \
  -e JAVA_OPTS="-Xmx1024m -Xms512m" \
  -e DATABASE_URL="postgresql://postgres:postgres@host.docker.internal:5432/todolist" \
  todolist-backend
```

### Docker Compose Setup

Create `docker-compose.yml`:

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15-alpine
    container_name: postgres-db
    environment:
      POSTGRES_DB: todolist
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - todolist-network

  backend:
    build: .
    container_name: todolist-api
    environment:
      DATABASE_URL: postgresql://postgres:postgres@postgres:5432/todolist
      PORT: 8080
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    networks:
      - todolist-network

networks:
  todolist-network:
    driver: bridge

volumes:
  postgres_data:
```

Run with Docker Compose:
```bash
# Start services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

### Docker Management Commands

```bash
# View running containers
docker ps

# View logs
docker logs todolist-api

# View live logs
docker logs -f todolist-api

# Stop container
docker stop todolist-api

# Start container
docker start todolist-api

# Remove container
docker rm todolist-api

# Remove image
docker rmi todolist-backend
```

## Testing the API

### Live API Testing

You can test the live API without any setup:

```bash
# Get all todos from live server
curl https://todoapp-en1q.onrender.com/api/todos

# Create a todo on live server
curl -X POST https://todoapp-en1q.onrender.com/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Todo","description":"Testing the live API"}'

# Get specific todo
curl https://todoapp-en1q.onrender.com/api/todos/1

# Search todos
curl https://todoapp-en1q.onrender.com/api/todos?search=test
```

### Using cURL (Local Development)

```bash
# Create a todo
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Test Todo","description":"Testing the API"}'

# Get all todos
curl http://localhost:8080/api/todos

# Get specific todo
curl http://localhost:8080/api/todos/1

# Update todo
curl -X PUT http://localhost:8080/api/todos/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Todo","description":"Updated description","completed":true}'

# Toggle completion
curl -X PATCH http://localhost:8080/api/todos/1/toggle

# Delete todo
curl -X DELETE http://localhost:8080/api/todos/1
```

### Using Postman

1. Import the API endpoints into Postman
2. Set base URL: 
   - **Local:** `http://localhost:8080/api/todos`
   - **Live:** `https://todoapp-en1q.onrender.com/api/todos`
3. Test each endpoint with appropriate HTTP methods

## Troubleshooting

### Common Issues

**Issue: Application fails to start**
```
Solution: Check if DATABASE_URL is set correctly
$ echo $DATABASE_URL
```

**Issue: Connection refused to PostgreSQL**
```
Solution: Verify PostgreSQL is running
$ docker ps  # if using Docker
$ pg_isready  # if using local installation
```

**Issue: Port already in use**
```
Solution: Change port
$ java -Dserver.port=9090 -jar target/*.jar
```

**Issue: Maven build fails**
```
Solution: Clean and rebuild
$ ./mvnw clean install -U
```

## CORS Configuration

The API has CORS enabled with `@CrossOrigin(origins = "*")` in the controller, allowing requests from any origin. This is suitable for development but should be restricted in production:

```java
@CrossOrigin(origins = "https://yourdomain.com")
```

## Project Dependencies

From `pom.xml`:

- `spring-boot-starter-web` - REST API support
- `spring-boot-starter-data-jpa` - JPA and Hibernate
- `postgresql` - PostgreSQL driver
- `spring-boot-starter-test` - Testing support

## Author

**Aryan**
- GitHub: [@DebugWithAryan](https://github.com/DebugWithAryan)
- Repository: [ToDoList-Backend](https://github.com/DebugWithAryan/ToDoList-Backend-)

## License

This project is open source and available for educational purposes.
