package com.studentmanagement.sms_api.service;

import com.studentmanagement.sms_api.dto.request.StudentRequestDto;
import com.studentmanagement.sms_api.dto.response.StudentResponseDto;

import java.util.List;

public interface StudentService {
    StudentResponseDto findById(Long id);
    List<StudentResponseDto> findAll();
    StudentResponseDto create(StudentRequestDto dto);
    StudentResponseDto update(Long id, StudentRequestDto dto);
    void delete(Long id);
}
