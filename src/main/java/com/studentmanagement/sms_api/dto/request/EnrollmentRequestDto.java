package com.studentmanagement.sms_api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class EnrollmentRequestDto {
    @NotNull
    private Long studentId;
    @NotNull
    private Long subjectId;
    @NotNull
    private LocalDate enrollmentDate;
}
