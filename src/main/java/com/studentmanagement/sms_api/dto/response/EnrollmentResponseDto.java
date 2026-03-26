package com.studentmanagement.sms_api.dto.response;

import com.studentmanagement.sms_api.model.enums.EnrollmentStatus;

import java.time.LocalDate;
import lombok.Data;

@Data
public class EnrollmentResponseDto {
    private Long id;
    private String studentName;
    private String subjectName;
    private Double grade;
    private LocalDate enrollmentDate;
    private EnrollmentStatus status;
}
