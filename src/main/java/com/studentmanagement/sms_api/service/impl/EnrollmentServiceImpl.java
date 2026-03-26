package com.studentmanagement.sms_api.service.impl;

import com.studentmanagement.sms_api.dto.request.EnrollmentRequestDto;
import com.studentmanagement.sms_api.dto.response.EnrollmentResponseDto;
import com.studentmanagement.sms_api.exception.DuplicateResourceException;
import com.studentmanagement.sms_api.exception.InvalidOperationException;
import com.studentmanagement.sms_api.exception.ResourceNotFoundException;
import com.studentmanagement.sms_api.mapper.EnrollmentMapper;
import com.studentmanagement.sms_api.model.Enrollment;
import com.studentmanagement.sms_api.model.Student;
import com.studentmanagement.sms_api.model.Subject;
import com.studentmanagement.sms_api.model.enums.EnrollmentStatus;
import com.studentmanagement.sms_api.repository.EnrollmentRepository;
import com.studentmanagement.sms_api.repository.StudentRepository;
import com.studentmanagement.sms_api.repository.SubjectRepository;
import com.studentmanagement.sms_api.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    private final EnrollmentMapper enrollmentMapper;

    @Override
    public EnrollmentResponseDto findById(Long id) {
        return enrollmentMapper.toResponseDto(enrollmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Enrollment not found with id: " + id)
        ));
    }

    @Override
    public List<EnrollmentResponseDto> findByStudentId(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        return enrollments.stream().map(enrollmentMapper :: toResponseDto).toList();
    }

    @Override
    public List<EnrollmentResponseDto> findBySubjectId(Long subjectId) {
        List<Enrollment> enrollments = enrollmentRepository.findBySubjectId(subjectId);
        return enrollments.stream().map(enrollmentMapper :: toResponseDto).toList();
    }

    @Override
    @Transactional
    public EnrollmentResponseDto enroll(EnrollmentRequestDto dto) {
        Student student = studentRepository.findById(dto.getStudentId()).orElseThrow(
                () -> new ResourceNotFoundException("Student not found with id: " + dto.getStudentId())
        );

        Subject subject = subjectRepository.findById(dto.getSubjectId()).orElseThrow(
                () -> new ResourceNotFoundException("Subject not found with id: " + dto.getSubjectId())
        );

        if(enrollmentRepository.existsByStudentIdAndSubjectId(dto.getStudentId(), dto.getSubjectId())) {
            throw new DuplicateResourceException("Student already enrolled in this subject");
        }
        Enrollment enrollment = enrollmentMapper.toEntity(dto);
        enrollment.setStudent(student);
        enrollment.setSubject(subject);
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        return enrollmentMapper.toResponseDto(enrollmentRepository.save(enrollment));
    }

    @Override
    @Transactional
    public EnrollmentResponseDto updateGrade(Long id, Double grade) {

        if(grade < 0 || grade > 10) {
            throw new InvalidOperationException("Grade must be between 0 and 10");
        }

        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Enrollment not found with id: " + id)
        );

        enrollment.setGrade(grade);
        if(grade >= 5.0) {
            enrollment.setStatus(EnrollmentStatus.APPROVED);
        } else {
            enrollment.setStatus(EnrollmentStatus.SUSPENDED);
        }

        return enrollmentMapper.toResponseDto(enrollmentRepository.save(enrollment));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Enrollment not found with id: " + id)
        );
        if(enrollment.getGrade() != null) {
            throw new InvalidOperationException("Cannot delete an enrollment with a grade assigned");
        }
        enrollmentRepository.deleteById(id);
    }
}
