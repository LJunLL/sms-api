package com.studentmanagement.sms_api.service.impl;

import com.studentmanagement.sms_api.exception.InvalidOperationException;
import com.studentmanagement.sms_api.mapper.SubjectMapper;
import com.studentmanagement.sms_api.repository.CourseRepository;
import com.studentmanagement.sms_api.repository.EnrollmentRepository;
import com.studentmanagement.sms_api.repository.SubjectRepository;
import com.studentmanagement.sms_api.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private SubjectMapper subjectMapper;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Test
    void delete_throwsInvalidOperationException_whenSubjectHasEnrollments(){
        // Arrange
        when(subjectRepository.existsById(1L)).thenReturn(true);
        when(enrollmentRepository.existsBySubjectId(1L)).thenReturn(true);

        // Act & Assert
        assertThrows(InvalidOperationException.class, () -> subjectService.delete(1L));
        verify(subjectRepository, never()).deleteById(any());
    }

    @Test
    void delete_deletesSubject_whenSubjectHasNoEnrollments(){
        // Arrange
        when(subjectRepository.existsById(1L)).thenReturn(true);

        // Act
        subjectService.delete(1L);

        // Assert
        verify(subjectRepository).deleteById(1L);
    }
}