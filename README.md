# Job Portal

A Spring Boot REST API for a job portal platform, supporting company listings, job postings, contact requests, and JWT-based authentication with role-based access control.

## Tech Stack

| Layer          | Technology                                |
|----------------|--------------------------------------------|
| Language       | Java 21                                    |
| Framework      | Spring Boot 4.1.0                          |
| Persistence    | Spring Data JPA / Hibernate                |
| Database       | MySQL 8+ (via Docker Compose)              |
| Security       | Spring Security, JJWT (JSON Web Tokens)    |
| API Docs       | springdoc-openapi (Swagger UI)             |
| Build Tool     | Maven                                      |
| Utilities      | Lombok, Jakarta Bean Validation            |

## Project Structure

```
src/main/java/org/jobportal/portal/
├── auth/                      # Login/registration controller
├── company/                    # Company controller & service layer
├── contact/                    # Contact form controller & service layer
├── job/                        # Job management controller & service layer
├── user/                       # User management controller & service layer
├── security/                   # Spring Security config, JWT filter, auth provider, CORS
├── audit/                      # JPA auditing (created_by / updated_by resolution)
├── entity/                     # JPA entities (User, Company, Job, Role, Contact, Profile, JobApplication)
├── repository/                 # Spring Data JPA repositories with custom queries
├── dto/                        # Request/response DTOs (input/output mapping)
├── constants/                  # Shared application constants
├── util/                       # Helper utilities (transformation, logging)
├── cache/                      # Caching configuration (Caffeine)
├── exception/                  # Global exception handling
├── aspects/                    # AOP aspects (logging, auditing, validation)
└── config/                     # Web/MVC configuration
```

## Prerequisites

- JDK 21+
- Maven 3.9+ (or use the bundled `./mvnw`)
- Docker Desktop (the app manages its own MySQL container via Spring Boot Docker Compose support)

## Getting Started

### 1. Clone and configure environment variables

The application reads sensitive configuration from environment variables (see [Configuration](#configuration) below). At minimum, set `DATABASE_PASSWORD` — it is used both by the app's datasource connection and by the Dockerized MySQL container's root password (single source of truth, never hardcoded):

```bash
export DATABASE_PASSWORD=<your-secure-password>
```

### 2. Database

The project uses **Spring Boot's Docker Compose integration** — starting the app automatically starts a MySQL container defined in [`compose.yml`](./compose.yml) and stops it on shutdown. No manual `docker compose up` is required.

```yaml
# compose.yml
services:
  dbservice:
    image: mysql:latest
    container_name: jobportaldb
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: ${DATABASE_PASSWORD}
      MYSQL_DATABASE: jobportal
```

Schema is created/updated automatically on startup (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Seed reference data

Roles (`ROLE_JOB_SEEKER`, `ROLE_EMPLOYER`, `ROLE_ADMIN`) and any sample data are **not** committed to the repo (see `.gitignore`). Create your own seed script under `src/main/resources/sql/` and load it into the running container, e.g.:

```powershell
Get-Content "src/main/resources/sql/jobportal-data.sql" | docker exec -i jobportaldb mysql -u root -p$env:DATABASE_PASSWORD jobportal
```

> The `roles.name` column has a unique constraint — re-running the seed script without clearing existing rows first will fail with a duplicate-key error by design.

### 4. Run the application

```bash
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080/api`.

### 5. API documentation

Swagger UI: `http://localhost:8080/api/swagger-ui.html`
OpenAPI spec: `http://localhost:8080/api/v3/api-docs`

## Configuration

All configuration lives in [`application.properties`](./src/main/resources/application.properties) and is overridable via environment variables:

| Variable              | Default        | Description                                  |
|-----------------------|----------------|-----------------------------------------------|
| `DATABASE_HOST`        | `localhost`    | MySQL host                                    |
| `DATABASE_PORT`        | `3306`         | MySQL port                                    |
| `DATABASE_NAME`        | `jobportal`    | MySQL schema name                             |
| `DATABASE_USERNAME`    | `root`         | MySQL username                                |
| `DATABASE_PASSWORD`    | *(required)*   | MySQL password                                |
| `JWT_SECRET_KEY`       | dev default    | HMAC signing secret for JWTs — **override in production** |
| `SHOW_SQL`             | `true`         | Log Hibernate SQL statements                  |
| `HIBERNATE_FORMAT_SQL` | `true`         | Pretty-print logged SQL                       |
| `LOG_LEVEL`            | `INFO`         | Log level for `org.jobportal.portal`          |
| `LOG_LEVEL_ERROR`      | `ERROR`        | Log level for the `security`/`contact` logging group |

> **Production checklist:** always set an explicit, high-entropy `JWT_SECRET_KEY` — the built-in default is for local development only.

## Authentication & Authorization

- Authentication is stateless, via a JWT bearer token issued on login (`POST /api/auth/login/public`) and validated on subsequent requests by a custom `JwtTokenVaidatorFilter`.
- New accounts are self-registered via `POST /api/auth/register/public` and are assigned the `ROLE_JOB_SEEKER` role by default.
- Public (unauthenticated) paths are declared in [`PathsConfig`](./src/main/java/org/jobportal/portal/security/PathsConfig.java); everything else under `/api/**` requires a valid `Authorization: Bearer <token>` header.
- Passwords are hashed with BCrypt and checked against the [Have I Been Pwned](https://haveibeenpwned.com/) API at registration time to reject compromised passwords.

## API Overview

### Public Endpoints (No Auth Required)

| Method | Endpoint                        | Description                                    |
|--------|---------------------------------|------------------------------------------------|
| POST   | `/api/auth/register/public`     | Register a new job-seeker account              |
| POST   | `/api/auth/login/public`        | Authenticate and receive JWT token (24h expiry)|
| GET    | `/api/companies/public`         | List all companies with their job postings     |
| POST   | `/api/contacts/public`          | Submit a contact/inquiry form                  |

### Secured Endpoints (JWT Required)

| Method | Endpoint                         | Description                          |
|--------|----------------------------------|--------------------------------------|
| GET    | `/api/users/{userId}`            | Get user profile                     |
| PUT    | `/api/users/{userId}`            | Update user profile                  |
| GET    | `/api/jobs`                      | List jobs (with filtering)           |
| POST   | `/api/jobs`                      | Create new job posting               |
| PUT    | `/api/jobs/{jobId}`              | Update job posting                   |
| POST   | `/api/jobs/{jobId}/apply`        | Apply for a job                      |
| GET    | `/api/profiles`                  | Get user profile details             |
| POST   | `/api/profiles`                  | Create/update user profile           |

All endpoints support **API versioning** via `version` query parameter or `Accept` header (e.g., `version=1.0`). See Swagger UI for full OpenAPI spec and request/response schemas.

## Dependency Injection & Lombok

This project uses **constructor injection** with Lombok's `@RequiredArgsConstructor` — no `@Autowired` field injection. Benefits:

- ✅ Immutable dependencies (`final` fields)
- ✅ Easy to test (pass mocks to constructor)
- ✅ Clear dependency declaration in method signature
- ✅ Zero boilerplate (Lombok generates constructors)

Example:
```java
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements ICompanyService {
    private final CompanyRepository companyRepository;  // Injected automatically
    
    @Override
    public List<CompanyDto> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(ApplicationUtility::transformCompanyToDto)
                .toList();
    }
}
```

## Data Transformation (DTO ↔ Entity)

Always use explicit helper methods in `ApplicationUtility` for complex transformations:

- **Entity → DTO** (response): Use `ApplicationUtility.transformXxxToDto()` — manually GET from entity, populate DTO
- **DTO → Entity** (request): Use `ApplicationUtility.transformDtoToXxx()` — manually SET into entity, handle nested objects

**Never use `BeanUtils.copyProperties` for nested objects** — it silently loses data.

## Building

```bash
./mvnw clean package
```

Produces an executable jar at `target/portal-0.0.1-SNAPSHOT.jar`:

```bash
java -jar target/portal-0.0.1-SNAPSHOT.jar
```

## Running Tests

```bash
./mvnw test
```

## Environment Profiles

The application supports multiple deployment profiles via `spring.profiles.active`:

- **`dev`** (default): Local development with debug logging
- **`qa`**: Quality assurance environment (see `application-qa.properties`)
- **`prod`**: Production deployment (see `application-prod.properties`, requires secure env vars)

Set via environment variable:
```bash
export SPRING_PROFILES_ACTIVE=prod
```

Or in `application.properties`:
```properties
spring.profiles.active=prod
```

## License

Proprietary — all rights reserved unless a license is added to this repository.
