# Spring Boot CRUD API

A beginner-friendly RESTful API built using **Java, Spring Boot, MySQL, and Spring Data JPA** to perform CRUD (Create, Read, Update, Delete) operations on user records. This project demonstrates backend development fundamentals, database integration, exception handling, and API documentation using Swagger/OpenAPI.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Database Configuration](#database-configuration)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [API Documentation](#api-documentation)
- [Error Handling](#error-handling)
- [Learning Outcomes](#learning-outcomes)
- [Future Improvements](#future-improvements)
- [License](#license)

## Overview

This project provides a REST API for managing user records stored in a MySQL database. It uses Spring Boot to build the application, Spring Data JPA to interact with the database, and a layered architecture to separate request handling from data access.

## Features

- Create new user records.
- Retrieve all users.
- Retrieve a user by ID.
- Update existing user records.
- Delete user records.
- Persist data in MySQL.
- Perform database operations using Spring Data JPA.
- Handle exceptions, including users not found.
- Generate interactive API documentation using Swagger/OpenAPI, if configured.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java | Programming language |
| Spring Boot | Backend application framework |
| MySQL | Relational database |
| Spring Data JPA | Database access and persistence |
| Hibernate | ORM implementation |
| Maven | Dependency management and build automation |
| REST API | Communication over HTTP |
| Swagger/OpenAPI | API documentation, if configured |
| Eclipse IDE | Development environment |

## Project Structure

The structure below is illustrative. Adjust package names to match your implementation.

```text
src/
├── main/
│   ├── java/
│   │   └── com/example/project/
│   │       ├── controllers/
│   │       ├── entities/
│   │       ├── repositories/
│   │       ├── exceptions/
│   │       └── Application.java
│   └── resources/
│       └── application.properties
├── test/
│   └── java/
pom.xml
mvnw
mvnw.cmd
```

- **Controllers:** Handle incoming HTTP requests.
- **Entities:** Represent database tables.
- **Repositories:** Perform database operations.
- **Exceptions:** Define custom exceptions and error handling.
- **Application.java:** Entry point for the Spring Boot application.
- **application.properties:** Stores application and database configuration.
- **pom.xml:** Defines Maven dependencies and build settings.

## Prerequisites

Install the following before running the application:

- Java Development Kit (JDK) compatible with your Spring Boot version.
- MySQL Server.
- Eclipse IDE, IntelliJ IDEA, or another Java IDE (optional).
- Maven, or use the Maven Wrapper included in the project.

Verify your Java installation:

```bash
java -version
```

## Getting Started

### 1. Clone the Repository

Replace the URL below with your actual GitHub repository URL.

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY.git
cd YOUR_REPOSITORY
```

### 2. Create the MySQL Database

Log in to MySQL and execute:

```sql
CREATE DATABASE springboot_crud;
```

Use the database name configured in your application if it differs from this example.

## Database Configuration

Open `src/main/resources/application.properties` and configure your database connection.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/springboot_crud
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Set the `DB_USERNAME` and `DB_PASSWORD` environment variables to your MySQL credentials.

**Security note:** Never commit real database passwords or other secrets to GitHub. Use environment variables or a local configuration file excluded from version control.

The `spring.jpa.hibernate.ddl-auto=update` setting allows Hibernate to update the schema based on entity definitions. For production applications, use a database migration tool and manage schema changes carefully.

## Running the Application

Run the application from the project root.

**macOS / Linux**

```bash
./mvnw spring-boot:run
```

If necessary, make the wrapper executable:

```bash
chmod +x mvnw
```

**Windows**

```bat
mvnw.cmd spring-boot:run
```

Alternatively, if Maven is installed:

```bash
mvn spring-boot:run
```

You can also import the project into Eclipse and run the main class annotated with `@SpringBootApplication`.

The application typically runs at:

```text
http://localhost:8080
```

The port may differ depending on your configuration.

## API Endpoints

The following table shows a typical REST endpoint design. Confirm the paths and HTTP methods against your controller mappings.

| Operation | HTTP Method | Example Endpoint | Description |
|---|---|---|---|
| Get all users | GET | `/users` | Retrieves all users |
| Get user by ID | GET | `/users/{id}` | Retrieves a specific user |
| Create user | POST | `/users` | Creates a new user |
| Update user | PUT | `/users/{id}` | Updates an existing user |
| Delete user | DELETE | `/users/{id}` | Deletes a user |

If your controller defines a base path such as `/api/users`, include that prefix in the endpoints.

### Example: Create a User

Request:

```http
POST /users
Content-Type: application/json
```

Request body:

```json
{
  "name": "Abdullah",
  "email": "abdullah@example.com"
}
```

The JSON fields are illustrative. Use the properties defined in your `UserEntity`.

### Example: Retrieve a User

```http
GET /users/1
```

You can test the endpoints using Postman, `curl`, or Swagger UI if it is configured.

## API Documentation

If Swagger/OpenAPI is enabled, you can use the generated documentation to inspect endpoints and send requests interactively.

A common Swagger UI URL is:

```text
http://localhost:8080/swagger-ui/index.html
```

The OpenAPI specification is commonly available at:

```text
http://localhost:8080/v3/api-docs
```

These URLs require the appropriate documentation dependency and configuration.

## Error Handling

The application demonstrates handling errors such as requests for users that do not exist.

A custom exception, such as `ResourceNotFoundException`, can represent a missing user. Spring can map it to an appropriate HTTP response, such as `404 Not Found`, using `@ResponseStatus` or an exception handler.

Common HTTP status codes include:

| Status Code | Meaning |
|---|---|
| `200 OK` | Request completed successfully |
| `201 Created` | Resource created successfully |
| `204 No Content` | Request succeeded without a response body |
| `400 Bad Request` | Invalid request data |
| `404 Not Found` | Requested resource does not exist |
| `500 Internal Server Error` | Unexpected server-side error |

The actual status codes depend on your controller and exception-handling implementation.

## Learning Outcomes

By completing this project, you can practise:

- Building a REST API with Spring Boot.
- Mapping HTTP requests using controller annotations.
- Creating JPA entities and repository interfaces.
- Performing CRUD operations with Spring Data JPA.
- Connecting Spring Boot to MySQL.
- Configuring application properties.
- Handling exceptions and HTTP status codes.
- Testing REST endpoints using Postman or Swagger UI.
- Managing dependencies and running applications using Maven.

## Future Improvements

Potential enhancements include:

- Add request validation using Jakarta Bean Validation.
- Write unit and integration tests.
- Implement pagination and sorting.
- Introduce DTOs for request and response objects.
- Add centralized exception handling with `@ControllerAdvice`.
- Implement authentication and authorization with Spring Security.
- Manage database migrations using Flyway or Liquibase.
- Containerize the application using Docker.
- Deploy the API to a cloud platform.

---

## New: Caching and Batch Processing (beginner version)

### Setup change
The MySQL password is no longer in `application.properties`. Set it before running:

```bash
export MYSQL_USER=root
export MYSQL_PASSWORD='your-password'
```

### 1. Caching (`UserService.java`)

Caching means *remembering* an answer so we don't ask the database again.

| Method | Annotation | What it does |
|---|---|---|
| `getUserById` | `@Cacheable` | First time: read MySQL and remember. Next times: answer from memory. |
| `updateUser` | `@CachePut` | Save to MySQL and replace the remembered copy. |
| `deleteUser` | `@CacheEvict` | Delete from MySQL and forget the copy. |

Settings are 3 lines in `application.properties` (cache name `users`, max 500 users, forget after 10 minutes).
The cache lives inside this one running application, so it is lost on restart.

**Try it:**
```bash
curl -X POST localhost:8080/api/users -H "Content-Type: application/json" -d '{"name":"Abdullah","email":"a@example.com"}'
curl localhost:8080/api/users/1    # console prints "Reading user 1 from the DATABASE"
curl localhost:8080/api/users/1    # nothing printed -> answered from the cache
```

### 2. Batch processing (`BatchService.java`)

A *thread* is a worker that does a job. A *thread pool* is a fixed team of workers (here 4) that are reused.
When you send a list of user ids, each id is handed to the team, so several users are checked at the same time,
and you get a task id back immediately instead of waiting.

Each user check loads the user and reports `OK`, `INVALID` (empty name or email) or `NOT FOUND`.

**Try it:**
```bash
curl -X POST localhost:8080/api/users/process-batch -H "Content-Type: application/json" -d '{"userIds":[1,2,3]}'
# -> {"taskId":"abc-123"}

curl localhost:8080/api/users/process-batch/abc-123
# -> {"taskId":"abc-123","status":"COMPLETED","results":["User 1: OK [pool-2-thread-1]", "User 3: NOT FOUND [pool-2-thread-2]", ...]}
```
`status` is `RUNNING` until every user is checked, then `COMPLETED`. The name in `[...]` is the worker thread that did the job.

**Limitations:** task results are kept in memory only (lost on restart); there is no limit on how many jobs can wait in the queue.

### Run the tests
```bash
./mvnw test
```
`UserServiceCacheTest` and `BatchServiceTest` use a fake repository, so they don't need MySQL.
(The original `contextLoads` test does need MySQL.)
# spring-boot-caching-concurrency-demo
