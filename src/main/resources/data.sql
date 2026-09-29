-- Roles
MERGE INTO roles (name) KEY(name) VALUES ('ROLE_USER');
MERGE INTO roles (name) KEY(name) VALUES ('ROLE_ADMIN');

-- Teachers
INSERT INTO teachers (name, last_name, email, phone, specialty) VALUES
('John', 'Smith', 'john.smith@school.com', '612345678', 'Mathematics'),
('Sarah', 'Johnson', 'sarah.johnson@school.com', '623456789', 'Programming'),
('Michael', 'Brown', 'michael.brown@school.com', '634567890', 'Databases'),
('Emily', 'Davis', 'emily.davis@school.com', '645678901', 'Networks');

-- Courses
INSERT INTO courses (name, hours, course_level, description) VALUES
('DAM 1', 2000, 1, 'First year of Multiplatform Application Development'),
('DAM 2', 2000, 2, 'Second year of Multiplatform Application Development');

-- Subjects
INSERT INTO subjects (name, desc, hours, teacher_id, course_id) VALUES
('Programming', 'Introduction to programming with Java', 8, 2, 1),
('Databases', 'Relational databases and SQL', 6, 3, 1),
('Operating Systems', 'Linux and Windows administration', 5, 4, 1),
('Access to Data', 'Advanced data access with JPA and Hibernate', 8, 3, 2),
('Multimedia Development', 'UI/UX and multimedia applications', 6, 1, 2),
('Web Services', 'REST APIs and web services development', 8, 2, 2);

-- Students
INSERT INTO students (dni, name, last_name, email, phone, birthday, enrollment_date) VALUES
('12345678A', 'Carlos', 'Garcia', 'carlos.garcia@student.com', '611111111', '2000-05-15', '2023-09-01'),
('23456789B', 'Maria', 'Lopez', 'maria.lopez@student.com', '622222222', '2001-03-22', '2023-09-01'),
('34567890C', 'Juan', 'Martinez', 'juan.martinez@student.com', '633333333', '1999-11-08', '2023-09-01'),
('45678901D', 'Ana', 'Sanchez', 'ana.sanchez@student.com', '644444444', '2002-07-30', '2023-09-01'),
('56789012E', 'Luis', 'Fernandez', 'luis.fernandez@student.com', '655555555', '2000-01-12', '2024-09-01');

-- Users (admin is created by DataSeeder on startup)
INSERT INTO users (username, email, password) VALUES
('user', 'user@school.com', '$2a$10$5PKeoFmpbe8fBv9v9HS4deXM.By05SN8GjfCHWUSUxD9mFZey.hXm');

-- Users_Roles
INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id FROM users u, roles r
WHERE u.username = 'user' AND r.name = 'ROLE_USER';

-- Enrollments
INSERT INTO enrollments (student_id, subject_id, enrollment_date, status) VALUES
(1, 1, '2023-09-01', 'ENROLLED'),
(1, 2, '2023-09-01', 'ENROLLED'),
(2, 1, '2023-09-01', 'APPROVED'),
(2, 2, '2023-09-01', 'SUSPENDED'),
(3, 1, '2023-09-01', 'ENROLLED'),
(4, 3, '2023-09-01', 'ENROLLED'),
(5, 4, '2024-09-01', 'ENROLLED');