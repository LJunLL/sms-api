package com.studentmanagement.sms_api.service;

import com.studentmanagement.sms_api.dto.request.TeacherRequestDto;
import com.studentmanagement.sms_api.dto.response.TeacherResponseDto;

import java.util.List;

public interface TeacherService {
    TeacherResponseDto findById(Long id);
    List<TeacherResponseDto> findAll();
    TeacherResponseDto create(TeacherRequestDto dto);
    TeacherResponseDto update(Long id, TeacherRequestDto dto);
    void delete(Long id);
}
