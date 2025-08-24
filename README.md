# Course Platform REST API

This project is a simple REST API for a course platform, built with Spring Boot.

## Features

*   Manage authors, courses, schools, and students.
*   RESTful API for all resources.

## Technology Stack

*   **Framework:** Spring Boot 3.5.4
*   **Language:** Java 21
*   **Database:** MySQL
*   **Data Access:** Spring Data JPA

## Getting Started

### Prerequisites

*   Java 21
*   Maven
*   MySQL

### Installation

1.  Clone the repository:

```bash
git clone https://github.com/your-username/your-repository.git
```

2.  Configure the database connection in `src/main/resources/application.yml`.

3.  Build the project:

```bash
./mvnw clean install
```

### Running the Application

To run the application, use the following command:

```bash
./mvnw spring-boot:run
```

The application will be available at `http://localhost:8080`.

## API Endpoints

The following are the main API endpoints:

*   `GET /api/authors`: Get all authors
*   `GET /api/authors/{id}`: Get an author by ID
*   `POST /api/authors`: Create a new author
*   `PUT /api/authors/{id}`: Update an author
*   `DELETE /api/authors/{id}`: Delete an author

*   `GET /api/courses`: Get all courses
*   `GET /api/courses/{id}`: Get a course by ID
*   `POST /api/courses`: Create a new course
*   `PUT /api/courses/{id}`: Update a course
*   `DELETE /api/courses/{id}`: Delete a course

*   `GET /api/schools`: Get all schools
*   `GET /api/schools/{id}`: Get a school by ID
*   `POST /api/schools`: Create a new school
*   `PUT /api/schools/{id}`: Update a school
*   `DELETE /api/schools/{id}`: Delete a school

*   `GET /api/students`: Get all students
*   `GET /api/students/{id}`: Get a student by ID
*   `POST /api/students`: Create a new student
*   `PUT /api/students/{id}`: Update a student
*   `DELETE /api/students/{id}`: Delete a student
