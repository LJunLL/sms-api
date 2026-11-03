package com.studentmanagement.sms_api.service.impl;

import com.studentmanagement.sms_api.dto.request.EnrollmentRequestDto;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private EnrollmentMapper enrollmentMapper;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Test
    void enroll_throwsDuplicateResourceException_whenStudentAlreadyEnrolled() {
        // Arrange
        EnrollmentRequestDto dto = new EnrollmentRequestDto();
        dto.setStudentId(1L);
        dto.setSubjectId(2L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student()));
        when(subjectRepository.findById(2L)).thenReturn(Optional.of(new Subject()));
        when(enrollmentRepository.existsByStudentIdAndSubjectId(1L, 2L)).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> enrollmentService.enroll(dto));
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void enroll_throwsResourceNotFoundException_whenStudentDoesNotExist() {
        // Arrange
        EnrollmentRequestDto dto = new EnrollmentRequestDto();
        dto.setStudentId(1L);
        dto.setSubjectId(2L);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> enrollmentService.enroll(dto));
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void enroll_savesWithEnrolledStatus_whenValid() {
        // Arrange
        EnrollmentRequestDto dto = new EnrollmentRequestDto();
        dto.setStudentId(1L);
        dto.setSubjectId(2L);
        dto.setEnrollmentDate(LocalDate.of(2026, 11, 3));

        Student student = new Student();
        student.setEnrollmentDate(LocalDate.of(2026, 11, 1));
        Subject subject = new Subject();
        Enrollment enrollment = new Enrollment();

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(2L)).thenReturn(Optional.of(subject));
        when(enrollmentMapper.toEntity(dto)).thenReturn(enrollment);

        // Act
        enrollmentService.enroll(dto);

        // Assert
        assertEquals(EnrollmentStatus.ENROLLED, enrollment.getStatus());
        assertEquals(student, enrollment.getStudent());
        assertEquals(subject, enrollment.getSubject());
        verify(enrollmentRepository).save(enrollment);
    }

    @Test
    void updateGrade_setsApprovedStatus_whenGradeIsFiveOrHigher() {
        // Arrange
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.ENROLLED);

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        // Act
        enrollmentService.updateGrade(1L, 7.5);

        // Assert
        assertEquals(7.5, enrollment.getGrade());
        assertEquals(EnrollmentStatus.APPROVED, enrollment.getStatus());
        verify(enrollmentRepository).save(enrollment);
    }

    @Test
    void updateGrade_setsSuspendedStatus_whenGradeIsBelowFive(){
        // Arrange
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.ENROLLED);

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        // Act
        enrollmentService.updateGrade(1L, 4.5);

        // Assert
        assertEquals(4.5, enrollment.getGrade());
        assertEquals(EnrollmentStatus.SUSPENDED, enrollment.getStatus());
        verify(enrollmentRepository).save(enrollment);
    }

    @Test
    void updateGrade_setsApprovedStatus_whenGradeIsExactlyFive(){
        // Arrange
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.ENROLLED);

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        // Act
        enrollmentService.updateGrade(1L, 5.0);

        // Assert
        assertEquals(5.0, enrollment.getGrade());
        assertEquals(EnrollmentStatus.APPROVED, enrollment.getStatus());
        verify(enrollmentRepository).save(enrollment);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -5.0, 10.1, 15.0})
    void updateGrade_throwsInvalidOperationException_whenGradeIsOutOfRange(double grade) {
        // Act & Assert
        assertThrows(InvalidOperationException.class, () -> enrollmentService.updateGrade(1L, grade));
        verify(enrollmentRepository, never()).save(any());
    }

    @Test
    void delete_throwsInvalidOperationException_whenEnrollmentHasGrade() {
        // Arrange
        Enrollment enrollment = new Enrollment();
        enrollment.setGrade(8.5);

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        // Act & Assert
        assertThrows(InvalidOperationException.class, () -> enrollmentService.delete(1L));
        verify(enrollmentRepository, never()).deleteById(any());
    }

    @Test
    void delete_deletesEnrollment_whenItHasNoGrade() {
        // Arrange
        Enrollment enrollment = new Enrollment();

        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));

        // Act
        enrollmentService.delete(1L);

        // Assert
        verify(enrollmentRepository).deleteById(1L);
    }

    @Test
    void enroll_throwsInvalidOperationException_whenDateIsBeforeStudentEnrollmentDate() {
        // Arrange
        EnrollmentRequestDto dto = new EnrollmentRequestDto();
        dto.setStudentId(1L);
        dto.setSubjectId(2L);
        dto.setEnrollmentDate(LocalDate.of(2026, 11, 1));

        Student student = new Student();
        student.setEnrollmentDate(LocalDate.of(2026, 11, 3));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(2L)).thenReturn(Optional.of(new Subject()));

        // Act & Assert
        assertThrows(InvalidOperationException.class, () -> enrollmentService.enroll(dto));
        verify(enrollmentRepository, never()).save(any());
    }
}