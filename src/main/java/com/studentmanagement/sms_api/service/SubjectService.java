package com.studentmanagement.sms_api.service;

import com.studentmanagement.sms_api.dto.request.SubjectRequestDto;
import com.studentmanagement.sms_api.dto.response.SubjectResponseDto;

import java.util.List;

public interface SubjectService {
    SubjectResponseDto findById(Long id);
    List<SubjectResponseDto> findAll();
    List<SubjectResponseDto> findByCourseId(Long courseId);
    List<SubjectResponseDto> findByTeacherId(Long teacherId);
    SubjectResponseDto create(SubjectRequestDto dto);
    SubjectResponseDto update(Long id, SubjectRequestDto dto);
    void delete(Long id);
}
