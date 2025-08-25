# Copilot Instructions for this Repository

These instructions help AI coding agents work effectively in this Spring Boot + Maven project.

## Architecture and layering
- API layer: REST controllers in `src/main/java/com/example/webapp/api/controllers`.
  - `StudentController` expose CRUD under `/api/students`.
  - Global error handling via `GlobalExceptionHandler` using RFC 7807 `ProblemDetail` + `traceId` property.
- Application layer: services, mappers, repositories under `src/main/java/com/example/webapp/application/*`.
  - Service interfaces + `*ServiceImpl` contain business logic.
  - DTOs as Java records in `application/*/dto`.
  - Mappers translate DTOs ⇄ entities. 
  - Repositories are Spring Data JPA `JpaRepository` interfaces (e.g., `StudentRepository`).
- Domain layer: JPA entities in `src/main/java/com/example/webapp/domain/entities`.

## Conventions and patterns
- DTO validation: Use `jakarta.validation` on Create DTOs; controllers apply `@Valid` on `@RequestBody`.
- Mapper policy: Keep mappers simple, side-effect free, and no lookups/throws; relations set via `getReferenceById`.
- Service policy: Services perform existence checks and orchestrate persistence. Controllers are thin.
- Errors: Always return Problem Details. Include `errors` array for validation failures and a `traceId` property for correlation.
- Endpoints: JSON-only. Status codes: 201 on create, 200 on read/update, 204 on delete. Not found → 404; bad input → 400; integrity issues → 409; unexpected → 500.

## Build, run, test
- Build jar (Windows PowerShell):
  - `./mvnw.cmd -DskipTests package`
- Run app (default profile):
  - `java -jar target/webapp-0.0.1-SNAPSHOT.jar`
- Tests: currently minimal; surefire is configured but tests are skipped with `-DskipTests`.

## Configuration and persistence
- Config at `src/main/resources/application.yml`.
  - MySQL URL: `jdbc:mysql://localhost:3306/springbootdemodb`
  - JPA: `hibernate.ddl-auto=create`, `show-sql=true`, MySQL dialect.
- Entities: `Student` has unique `email`.

## File map (key examples)
- Controllers: `api/controllers/*Controller.java`, `GlobalExceptionHandler.java`
- Services: `application/*/*Service.java`, `*ServiceImpl.java`
- DTOs: `application/*/dto/*.java`
- Mappers: `application/*/*Mapper.java`
- Repos: `application/*/*Repository.java`
- Entities: `domain/entities/*.java`

## How to extend features (examples)
- Add a new endpoint:
  1) Define DTO(s) in `application/<feature>/dto` with validation annotations.
  2) Update mapper to translate DTOs to entities (no DB fetch inside mapper).
  3) Add method to `*Service`/`*ServiceImpl` using repositories; validate IDs here.
  4) Expose in a `*Controller`, annotate `@Valid`.
  5) Errors will flow to the global handler as ProblemDetail.


## Important gotchas for agents
- Don’t move files across layers; follow the package conventions above.
- Mapper must not throw; services own validation and integrity checks.
- With `ddl-auto=create`, DB is recreated on each run; don’t rely on persisted data between runs.
- When adding validation, ensure controllers use `@Valid`; validation errors are handled centrally.
- Favor `ProblemDetail` responses; add `errors` on validation and keep `traceId` intact on all problems.
