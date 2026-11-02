package com.studentmanagement.sms_api.service.impl;

import com.studentmanagement.sms_api.exception.InvalidOperationException;
import com.studentmanagement.sms_api.mapper.CourseMapper;
import com.studentmanagement.sms_api.repository.CourseRepository;
import com.studentmanagement.sms_api.repository.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void delete_throwsInvalidOperationException_whenCourseHasSubjects(){
        // Arrange
        when(courseRepository.existsById(1L)).thenReturn(true);
        when(subjectRepository.existsByCourseId(1L)).thenReturn(true);

        // Act & Assert
        assertThrows(InvalidOperationException.class, () -> courseService.delete(1L));
        verify(courseRepository, never()).deleteById(any());
    }

    @Test
    void delete_deletesCourse_whenCourseHasNoSubjects(){
        // Arrange
        when(courseRepository.existsById(1L)).thenReturn(true);

        // Act
        courseService.delete(1L);

        // Assert
        verify(courseRepository).deleteById(1L);
    }
}