package com.studentmanagement.sms_api.service.impl;

import com.studentmanagement.sms_api.exception.InvalidOperationException;
import com.studentmanagement.sms_api.mapper.StudentMapper;
import com.studentmanagement.sms_api.repository.EnrollmentRepository;
import com.studentmanagement.sms_api.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void delete_throwsInvalidOperationException_whenStudentHasEnrollments() {
        // Arrange
        when(studentRepository.existsById(1L)).thenReturn(true);
        when(enrollmentRepository.existsByStudentId(1L)).thenReturn(true);

        // Act & Assert
        assertThrows(InvalidOperationException.class, () -> studentService.delete(1L));
        verify(studentRepository, never()).deleteById(any());
    }

    @Test
    void delete_deletesStudent_whenStudentHasNoEnrollments() {
        // Arrange
        when(studentRepository.existsById(1L)).thenReturn(true);

        // Act
        studentService.delete(1L);

        // Assert
        verify(studentRepository).deleteById(1L);
    }
}