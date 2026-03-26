package com.studentmanagement.sms_api.repository;

import com.studentmanagement.sms_api.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);
    List<Enrollment> findBySubjectId(Long subjectId);
    Optional<Enrollment> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
    boolean existsByStudentIdAndSubjectId(Long studentId, Long subjectId);
    boolean existsByStudentIdAndSubjectIdAndIdNot(Long studentId, Long subjectId, Long id);
}
