package com.studentmanagement.sms_api.config;

import com.studentmanagement.sms_api.model.*;
import com.studentmanagement.sms_api.model.enums.EnrollmentStatus;
import com.studentmanagement.sms_api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
@Order(2)
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.demo.enabled", havingValue = "true")
public class DemoDataSeeder implements CommandLineRunner {

    private static final String DEMO_USERNAME = "demo";
    private static final String DEMO_EMAIL = "demo@sms-demo.com";
    private static final String DEMO_ADMIN_USERNAME = "demo-admin";
    private static final String DEMO_ADMIN_EMAIL = "demo-admin@sms-demo.com";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Value("${app.demo.password}")
    private String demoPassword;

    @Override
    @Transactional
    public void run(String... args) {
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("ROLE_USER not found. DataSeeder must run first."));
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new IllegalStateException("ROLE_ADMIN not found. DataSeeder must run first."));

        createUserIfMissing(DEMO_USERNAME, DEMO_EMAIL, userRole);
        createUserIfMissing(DEMO_ADMIN_USERNAME, DEMO_ADMIN_EMAIL, adminRole);

        if (studentRepository.count() == 0) {
            createSampleData();
        }
    }

    private void createUserIfMissing(String username, String email, Role role) {
        if (userRepository.existsByUsername(username)) {
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(demoPassword));
        user.setRoles(new HashSet<>(Set.of(role)));
        userRepository.save(user);
    }

    private void createSampleData() {
        Teacher john = createTeacher("John", "Smith", "john.smith@school.com", "612345678", "Mathematics");
        Teacher sarah = createTeacher("Sarah", "Johnson", "sarah.johnson@school.com", "623456789", "Programming");
        Teacher michael = createTeacher("Michael", "Brown", "michael.brown@school.com", "634567890", "Databases");
        Teacher emily = createTeacher("Emily", "Davis", "emily.davis@school.com", "645678901", "Networks");

        Course dam1 = createCourse("DAM 1", 2000L, 1, "First year of Multiplatform Application Development");
        Course dam2 = createCourse("DAM 2", 2000L, 2, "Second year of Multiplatform Application Development");

        Subject programming = createSubject("Programming", "Introduction to programming with Java", 8, sarah, dam1);
        Subject databases = createSubject("Databases", "Relational databases and SQL", 6, michael, dam1);
        Subject operatingSystems = createSubject("Operating Systems", "Linux and Windows administration", 5, emily, dam1);
        Subject dataAccess = createSubject("Access to Data", "Advanced data access with JPA and Hibernate", 8, michael, dam2);
        Subject multimedia = createSubject("Multimedia Development", "UI/UX and multimedia applications", 6, john, dam2);
        Subject webServices = createSubject("Web Services", "REST APIs and web services development", 8, sarah, dam2);

        Student carlos = createStudent("12345678A", "Carlos", "Garcia", "carlos.garcia@student.com", "611111111", "2000-05-15", "2023-09-01");
        Student maria = createStudent("23456789B", "Maria", "Lopez", "maria.lopez@student.com", "622222222", "2001-03-22", "2023-09-01");
        Student juan = createStudent("34567890C", "Juan", "Martinez", "juan.martinez@student.com", "633333333", "1999-11-08", "2023-09-01");
        Student ana = createStudent("45678901D", "Ana", "Sanchez", "ana.sanchez@student.com", "644444444", "2002-07-30", "2023-09-01");
        Student luis = createStudent("56789012E", "Luis", "Fernandez", "luis.fernandez@student.com", "655555555", "2000-01-12", "2024-09-01");
        Student laura = createStudent("67890123F", "Laura", "Gomez", "laura.gomez@student.com", "666666666", "2003-02-18", "2024-09-01");
        Student pablo = createStudent("78901234G", "Pablo", "Ruiz", "pablo.ruiz@student.com", "677777777", "2001-09-05", "2024-09-01");
        Student elena = createStudent("89012345H", "Elena", "Torres", "elena.torres@student.com", "688888888", "2002-12-27", "2024-09-01");

        // DAM 1, curso 2023: notas ya puestas
        createEnrollment(carlos, programming, 8.5, "2023-09-01");
        createEnrollment(carlos, databases, 6.0, "2023-09-01");
        createEnrollment(maria, programming, 7.5, "2023-09-01");
        createEnrollment(maria, databases, 3.5, "2023-09-01");
        createEnrollment(juan, programming, 4.0, "2023-09-01");
        createEnrollment(juan, operatingSystems, 5.5, "2023-09-01");
        createEnrollment(ana, operatingSystems, 9.0, "2023-09-01");
        createEnrollment(ana, databases, 7.0, "2023-09-01");

        // Curso 2024: algunas evaluadas y otras todavía en curso
        createEnrollment(carlos, dataAccess, null, "2024-09-01");
        createEnrollment(carlos, webServices, null, "2024-09-01");
        createEnrollment(maria, dataAccess, 6.5, "2024-09-01");
        createEnrollment(luis, programming, null, "2024-09-01");
        createEnrollment(luis, databases, 2.5, "2024-09-01");
        createEnrollment(laura, programming, null, "2024-09-01");
        createEnrollment(laura, operatingSystems, null, "2024-09-01");
        createEnrollment(pablo, programming, 5.0, "2024-09-01");
        createEnrollment(pablo, databases, null, "2024-09-01");
        createEnrollment(elena, multimedia, null, "2024-09-01");
    }

    private Teacher createTeacher(String name, String lastName, String email, String phone, String specialty) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setLastName(lastName);
        teacher.setEmail(email);
        teacher.setPhone(phone);
        teacher.setSpecialty(specialty);
        return teacherRepository.save(teacher);
    }

    private Course createCourse(String name, Long hours, Integer level, String description) {
        Course course = new Course();
        course.setName(name);
        course.setHours(hours);
        course.setLevel(level);
        course.setDescription(description);
        return courseRepository.save(course);
    }

    private Subject createSubject(String name, String desc, Integer hours, Teacher teacher, Course course) {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setDesc(desc);
        subject.setHours(hours);
        subject.setTeacher(teacher);
        subject.setCourse(course);
        return subjectRepository.save(subject);
    }

    private Student createStudent(String dni, String name, String lastName,
                                  String email, String phone, String birthday, String enrollmentDate) {
        Student student = new Student();
        student.setDni(dni);
        student.setName(name);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPhone(phone);
        student.setBirthday(LocalDate.parse(birthday));
        student.setEnrollmentDate(LocalDate.parse(enrollmentDate));
        return studentRepository.save(student);
    }

    private void createEnrollment(Student student, Subject subject, Double grade, String enrollmentDate) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setSubject(subject);
        enrollment.setGrade(grade);
        enrollment.setEnrollmentDate(LocalDate.parse(enrollmentDate));
        enrollment.setStatus(statusForGrade(grade));
        enrollmentRepository.save(enrollment);
    }

    private EnrollmentStatus statusForGrade(Double grade) {
        if (grade == null) {
            return EnrollmentStatus.ENROLLED;
        }
        return grade >= 5.0 ? EnrollmentStatus.APPROVED : EnrollmentStatus.SUSPENDED;
    }
}