# 📚 Course Platform REST API

[![Java 21](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/technologies/javase/21/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.4-green.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/Database-MySQL-orange.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

This project is a robust and scalable REST API for a course platform, built with Spring Boot. It demonstrates modern enterprise-level practices, including layered architecture, domain-driven design principles, enhanced logging, domain events, and comprehensive API documentation.

## ✨ Key Features

*   **Comprehensive CRUD Operations:** Manage Authors, Courses, Schools, and Students.
*   **RESTful API Design:** Clean and intuitive API endpoints following REST principles.
*   **Data Validation:** Robust input validation using Jakarta Bean Validation.
*   **Pagination & Filtering:** Efficient retrieval of large datasets with flexible filtering options.

## 🏛️ Architecture

The application follows the principles of **Onion Architecture** and **Domain-Driven Design (DDD)**, structured into three distinct layers to ensure clear separation of concerns, maintainability, and testability:

1.  **API Layer:**
    *   Contains REST controllers (`api/controllers`) responsible for handling HTTP requests and responses.
    *   Acts as the entry point for external consumers.

2.  **Application Layer:**
    *   Houses the core application logic, including services, mappers, and Data Transfer Objects (DTOs).
    *   Features are organized into dedicated packages (e.g., `application/author`).
    *   Orchestrates domain operations and manages transactions.

3.  **Domain Layer:**
    *   Defines the core business models, including aggregates, entities, and value objects.
    *   Encapsulates all business rules and invariants within aggregate root entities.
    *   Contains domain events that signify important occurrences within the business domain.

## 🚀 Technologies Used

*   **Framework:** Spring Boot 3.5.4
*   **Language:** Java 21
*   **Build Tool:** Apache Maven
*   **Database:** MySQL
*   **Data Access:** Spring Data JPA
*   **API Documentation:** Springdoc OpenAPI (Swagger UI)
*   **Logging:** Logback with Logstash Encoder (Structured Logging, Asynchronous Logging)
*   **Utility:** Lombok (boilerplate code reduction)
*   **Validation:** Jakarta Bean Validation

## 🏁 Getting Started

### Prerequisites

Ensure you have the following installed:

*   **Java Development Kit (JDK) 21**
*   **Apache Maven**
*   **MySQL Server** (running and accessible)

### Installation & Setup

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/your-repository.git
    cd webapp
    ```
2.  **Database Configuration:**
    *   Ensure your MySQL server is running.
    *   Create a database named `springbootdemodb`.
    *   Verify the database connection details in `src/main/resources/application.yml`.

### Building the Application

```bash
./mvnw clean install
```

### Running the Application

You can run the application using different profiles:

*   **Default Profile (Development):** Runs on `http://localhost:8080`.
    ```bash
    ./mvnw spring-boot:run
    ```
*   **Production Profile:** Runs on `http://localhost:8081` (as configured in `application-prod.yml`).
    ```bash
    ./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
    ```

### 🧪 Running Tests

```bash
./mvnw test
```

## 📖 API Documentation (Swagger UI)

The API endpoints are comprehensively documented using OpenAPI (Swagger UI). Once the application is running, you can access the interactive documentation at:

*   **Swagger UI:** `http://localhost:8080/docs/index.html` (or `http://localhost:8081/docs/index.html` if running in `prod` profile)
*   **OpenAPI Specification (JSON):** `http://localhost:8080/api-docs` (or `http://localhost:8081/api-docs`)

## ✨ Production-Grade Enhancements

This project incorporates several enhancements for enterprise-level readiness:

*   **Enhanced Logging:**
    *   **Structured Logging:** Logs are output in JSON format, making them easily parsable by log aggregation tools.
    *   **Contextual Logging:** Each request is tagged with a unique correlation ID (X-Correlation-ID header), which is automatically included in all log messages via MDC, enabling easy tracing of requests.
    *   **Asynchronous Logging:** Improves application performance by processing log events in a separate thread.
*   **Domain Events:**
    *   Demonstrates an in-process event-driven architecture using Spring's `ApplicationEventPublisher`.
    *   Example: `AuthorCreatedEvent` is published when a new author is created, allowing other components to react in a decoupled manner.
*   **Health Checks (Actuator):**
    *   Spring Boot Actuator is integrated to provide insights into the application's health and operational status.
    *   Endpoints like `/actuator/health` are available for monitoring and readiness/liveness probes.

## 👨‍💻 Development Workflow

When adding a new feature, follow these steps:

1.  **Define the Domain Model:** Create necessary aggregates, entities, and value objects in the `domain` package.
2.  **Implement the Application Logic:** Create a new package under `application` for the feature. Inside this package, create the service, repository interface, mapper, and DTOs.
3.  **Create the Controller:** Create a new controller in the `api/controllers` package to expose the feature as a REST API.

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a pull request.

## 📄 License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details.