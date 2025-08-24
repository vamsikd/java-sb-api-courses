# Gemini Code Assistant

This document provides a summary of the Spring Boot web application and instructions on how to build, run, and test the project.

## Project Overview

This project is a simple REST API for a course platform, built with Spring Boot. It follows the principles of Onion Architecture and Domain-Driven Design (DDD).

## Architecture

The application is structured in three layers:

1.  **API Layer:** Contains the REST controllers that handle HTTP requests and responses. All controllers reside in the `api/controllers` package.
2.  **Application Layer:** Contains the application logic, including services, mappers, and data transfer objects (DTOs). Each feature is organized in its own package under `application/{feature}`.
3.  **Domain Layer:** Contains the domain models, including aggregates, entities, and value objects. All business rules and invariants are defined in the aggregate root entities.

## Development Workflow

When adding a new feature, follow these steps:

1.  **Define the domain model:** Create the necessary aggregates, entities, and value objects in the `domain` package.
2.  **Implement the application logic:** Create a new package under `application` for the feature. Inside this package, create the service, repository interface, mapper, and DTOs.
3.  **Create the controller:** Create a new controller in the `api/controllers` package to expose the feature as a REST API.

## Key Technologies

*   **Framework:** Spring Boot 3.5.4
*   **Language:** Java 21
*   **Database:** MySQL
*   **Data Access:** Spring Data JPA
*   **API:** RESTful APIs

## Building and Running the Application

To build and run the application, you can use the following Maven command:

```bash
./mvnw spring-boot:run
```

The application will start on the default port 8080.

## Running Tests

To run the tests, use the following Maven command:

```bash
./mvnw test
```
