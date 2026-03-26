package com.studentmanagement.sms_api.repository;

import com.studentmanagement.sms_api.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    Optional<Student> findByDni(String dni);
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByDniAndIdNot(String dni, Long id);
}
