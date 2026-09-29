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

2. Configure environment variables for production
```
   DB_URL=your_neon_postgresql_url
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   JWT_SECRET=your_secret_key
```

3. Run in development mode (H2 in-memory database)
```bash
   mvn spring-boot:run
```

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
