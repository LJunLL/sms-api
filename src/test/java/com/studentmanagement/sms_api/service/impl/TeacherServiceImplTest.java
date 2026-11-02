package com.studentmanagement.sms_api.service.impl;

import com.studentmanagement.sms_api.exception.InvalidOperationException;
import com.studentmanagement.sms_api.mapper.TeacherMapper;
import com.studentmanagement.sms_api.repository.SubjectRepository;
import com.studentmanagement.sms_api.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    void delete_throwsInvalidOperationException_whenTeacherHasSubjects(){
        // Arrange
        when(teacherRepository.existsById(1L)).thenReturn(true);
        when(subjectRepository.existsByTeacherId(1L)).thenReturn(true);

        // Act & Arrange
        assertThrows(InvalidOperationException.class, () -> teacherService.delete(1L));
        verify(teacherRepository, never()).deleteById(any());
    }

    @Test
    void delete_deletesTeacher_whenTeacherHasNoSubjects(){
        // Arrange
        when(teacherRepository.existsById(1L)).thenReturn(true);

        // Act
        teacherService.delete(1L);

        // Assert
        verify(teacherRepository).deleteById(1L);
    }
}