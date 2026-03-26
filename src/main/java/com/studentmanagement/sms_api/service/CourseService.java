package com.studentmanagement.sms_api.service;

import com.studentmanagement.sms_api.dto.request.CourseRequestDto;
import com.studentmanagement.sms_api.dto.response.CourseResponseDto;

import java.util.List;

public interface CourseService {
    CourseResponseDto findById(Long id);
    List<CourseResponseDto> findAll();
    CourseResponseDto create(CourseRequestDto dto);
    CourseResponseDto update(Long id, CourseRequestDto dto);
    void delete(Long id);
}
