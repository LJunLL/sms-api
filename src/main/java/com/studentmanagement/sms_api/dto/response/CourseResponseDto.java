package com.studentmanagement.sms_api.dto.response;

import lombok.Data;

@Data
public class CourseResponseDto {
    private Long id;
    private String name;
    private Integer durationHours;
    private Integer level;
    private String description;
}
