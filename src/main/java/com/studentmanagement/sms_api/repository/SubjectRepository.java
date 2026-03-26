package com.studentmanagement.sms_api.repository;

import com.studentmanagement.sms_api.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByCourseId(Long courseId);
    List<Subject> findByTeacherId(Long teacherId);
    boolean existsByNameAndCourseId(String name, Long courseId);
    boolean existsByNameAndCourseIdAndIdNot(String name, Long courseId, Long id);
}