# Student Management System API

A RESTful API built with Java and Spring Boot for managing students, courses, subjects, teachers and enrollments in an educational institution.

**Live demo:** [ljunll-sms.netlify.app](https://ljunll-sms.netlify.app) · **API docs:** [Swagger UI](https://ljunll-sms-api.onrender.com/swagger-ui.html) · **Frontend repo:** [sms-frontend](https://github.com/LJunLL/sms-frontend)

> The API runs on a free hosting plan that sleeps after 15 minutes of inactivity. The first request after that can take up to a minute while the server wakes up.

## Demo Accounts

| Username | Password | Role | Access |
|----------|----------|------|--------|
| demo-admin | demo1234 | ADMIN | Full access: create, edit and delete records |
| demo | demo1234 | USER | Read only |

Demo data is shared between all visitors and is recreated automatically if the student table is empty when the server starts.

## Architecture

```
Angular frontend  ──►  Spring Boot API  ──►  PostgreSQL
   (Netlify)          (Render, Docker)        (Neon)
```

Both the API and the frontend are redeployed automatically on every push to `main`.

## Highlights

- **Stateless JWT authentication** with Spring Security and role-based authorization (`USER` / `ADMIN`) using `@PreAuthorize`.
- **Business rules enforced in the service layer**, returning meaningful HTTP status codes (see [Business Rules](#business-rules)).
- **Consistent error responses** through a global exception handler: every error returns `status`, `error` and `message`.
- **Environment-based configuration** with Spring profiles: H2 in development, PostgreSQL in production, and no secrets in the repository.
- **Data seeders** that create roles and the admin user on startup, plus optional demo data for production.
- **Containerized** with a multi-stage Dockerfile.

## Tech Stack

- Java 21
- Spring Boot 3.4
- Spring Security + JWT
- Spring Data JPA + Hibernate
- PostgreSQL (production) / H2 (development)
- MapStruct
- Lombok
- Springdoc OpenAPI (Swagger UI)
- Docker

## Getting Started

### Prerequisites

- Java 21
- Maven 3.8+

### Run in development mode

The default profile uses an H2 in-memory database loaded with sample data.

```bash
git clone https://github.com/LJunLL/sms-api.git
cd sms-api
mvn spring-boot:run
```

Development users:

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| user | password123 | USER |

Swagger UI is available at http://localhost:8080/swagger-ui.html. To test protected endpoints, log in via `/api/v1/auth/login`, copy the token and paste it into the **Authorize** button.

### Run with Docker

```bash
docker build -t sms-api .
docker run -p 8080:8080 sms-api
```

Without environment variables, the container starts with the development profile.

### Production configuration

Set the following environment variables:

```
SPRING_PROFILES_ACTIVE=prod
DB_URL=jdbc:postgresql://<host>/<database>?sslmode=require
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
JWT_SECRET=your_secret_key
ADMIN_USERNAME=your_admin_username
ADMIN_PASSWORD=your_strong_admin_password
ADMIN_EMAIL=your_admin_email
CORS_ALLOWED_ORIGINS=https://your-frontend-url
```

Optional:

```
JWT_EXPIRATION=3600000   # token lifetime in milliseconds (default: 1 hour)
DEMO_ENABLED=true        # create demo users and sample data (default: false)
DEMO_PASSWORD=demo1234   # password for the demo users
```

On startup, an ADMIN user is created from `ADMIN_USERNAME`, `ADMIN_PASSWORD` and `ADMIN_EMAIL` if it doesn't exist. Public registration (`/api/v1/auth/register`) always creates users with the USER role.

## API Endpoints

### Auth
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/v1/auth/register | Register new user | Public |
| POST | /api/v1/auth/login | Login and get JWT token, username and roles | Public |

### Students
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | /api/v1/students | Get all students | USER, ADMIN |
| GET | /api/v1/students/{id} | Get student by id | USER, ADMIN |
| POST | /api/v1/students | Create student | ADMIN |
| PUT | /api/v1/students/{id} | Update student | ADMIN |
| DELETE | /api/v1/students/{id} | Delete student | ADMIN |

### Courses
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | /api/v1/courses | Get all courses | USER, ADMIN |
| GET | /api/v1/courses/{id} | Get course by id | USER, ADMIN |
| POST | /api/v1/courses | Create course | ADMIN |
| PUT | /api/v1/courses/{id} | Update course | ADMIN |
| DELETE | /api/v1/courses/{id} | Delete course | ADMIN |

### Teachers
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | /api/v1/teachers | Get all teachers | USER, ADMIN |
| GET | /api/v1/teachers/{id} | Get teacher by id | USER, ADMIN |
| POST | /api/v1/teachers | Create teacher | ADMIN |
| PUT | /api/v1/teachers/{id} | Update teacher | ADMIN |
| DELETE | /api/v1/teachers/{id} | Delete teacher | ADMIN |

### Subjects
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | /api/v1/subjects | Get all subjects | USER, ADMIN |
| GET | /api/v1/subjects/{id} | Get subject by id | USER, ADMIN |
| GET | /api/v1/subjects/course/{courseId} | Get subjects by course | USER, ADMIN |
| GET | /api/v1/subjects/teacher/{teacherId} | Get subjects by teacher | USER, ADMIN |
| POST | /api/v1/subjects | Create subject | ADMIN |
| PUT | /api/v1/subjects/{id} | Update subject | ADMIN |
| DELETE | /api/v1/subjects/{id} | Delete subject | ADMIN |

### Enrollments
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | /api/v1/enrollments/{id} | Get enrollment by id | USER, ADMIN |
| GET | /api/v1/enrollments/students/{id} | Get enrollments by student | USER, ADMIN |
| GET | /api/v1/enrollments/subjects/{id} | Get enrollments by subject | USER, ADMIN |
| POST | /api/v1/enrollments | Enroll student in subject | ADMIN |
| PUT | /api/v1/enrollments/{id}/grade | Update enrollment grade | ADMIN |
| DELETE | /api/v1/enrollments/{id} | Delete enrollment | ADMIN |

## Authentication

This API uses JWT Bearer token authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your_token>
```

## Error Responses

All errors share the same format:

```json
{
  "status": 409,
  "error": "Conflict",
  "message": "Cannot delete a student with enrollments"
}
```

| Status | When |
|--------|------|
| 400 | Validation errors in the request body |
| 401 | Missing or invalid token, or wrong credentials |
| 403 | Authenticated but without the required role |
| 404 | Resource not found |
| 409 | Duplicate data or an operation not allowed by a business rule |

## Business Rules

- A student cannot be enrolled twice in the same subject
- Updating a grade automatically changes enrollment status to APPROVED (≥5) or SUSPENDED (<5)
- Enrollments with an assigned grade cannot be deleted
- A student with enrollments cannot be deleted

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.