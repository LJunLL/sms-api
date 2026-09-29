# Student Management System API

A RESTful API built with Java Spring Boot for managing students, courses, subjects, teachers and enrollments in an educational institution.

## Tech Stack

- Java 21
- Spring Boot 3.4
- Spring Security + JWT
- Spring Data JPA + Hibernate
- PostgreSQL (production) / H2 (development)
- MapStruct
- Lombok
- Springdoc OpenAPI (Swagger UI)

## Getting Started

### Prerequisites

- Java 21
- Maven 3.8+

### Installation

1. Clone the repository

```bash
   git clone https://github.com/LJunLL/sms-api.git
   cd sms-api
```

2. Run in development mode (default profile, H2 in-memory database with sample data)

```bash
   mvn spring-boot:run
```

3. For production, set the following environment variables

```
   SPRING_PROFILES_ACTIVE=prod
   DB_URL=your_neon_postgresql_url
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   JWT_SECRET=your_secret_key
   ADMIN_USERNAME=your_admin_username
   ADMIN_PASSWORD=your_strong_admin_password
   ADMIN_EMAIL=your_admin_email
```

## Default Users

On startup, an ADMIN user is created automatically if it doesn't exist, using the `ADMIN_USERNAME`, `ADMIN_PASSWORD` and `ADMIN_EMAIL` environment variables.

In development mode, the following users are available:

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| user | password123 | USER |

Public registration (`/api/v1/auth/register`) always creates users with the USER role.

## API Documentation

Interactive API documentation is available via Swagger UI once the app is running:

http://localhost:8080/swagger-ui.html

To test protected endpoints, log in via `/api/v1/auth/login`, copy the token and paste it in the **Authorize** button.

## API Endpoints

### Auth
| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | /api/v1/auth/register | Register new user | Public |
| POST | /api/v1/auth/login | Login and get JWT token | Public |

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

## Business Rules

- A student cannot be enrolled twice in the same subject
- Updating a grade automatically changes enrollment status to APPROVED (≥5) or SUSPENDED (<5)
- Enrollments with an assigned grade cannot be deleted

## License

MIT
