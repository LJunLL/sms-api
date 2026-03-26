package com.studentmanagement.sms_api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseRequestDto {
    @NotBlank
    private String name;
    @NotNull
    @Min(1)
    private Integer durationHours;
    @NotNull
    @Min(1)
    private Integer level;
    @NotBlank
    private String description;
}
