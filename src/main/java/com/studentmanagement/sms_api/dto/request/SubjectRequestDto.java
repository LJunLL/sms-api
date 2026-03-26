package com.studentmanagement.sms_api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubjectRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    @Min(1)
    private Integer weeklyHours;
    @NotNull
    private Long teacherId;
    @NotNull
    private Long courseId;
}
