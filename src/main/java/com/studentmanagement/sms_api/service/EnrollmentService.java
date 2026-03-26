package com.studentmanagement.sms_api.service;

import com.studentmanagement.sms_api.dto.request.EnrollmentRequestDto;
import com.studentmanagement.sms_api.dto.response.EnrollmentResponseDto;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponseDto findById(Long id);
    List<EnrollmentResponseDto> findByStudentId(Long studentId);
    List<EnrollmentResponseDto> findBySubjectId(Long subjectId);
    EnrollmentResponseDto enroll(EnrollmentRequestDto dto);
    EnrollmentResponseDto updateGrade(Long id, Double grade);
    void delete(Long id);
}
