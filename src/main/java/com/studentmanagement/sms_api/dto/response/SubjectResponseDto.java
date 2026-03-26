package com.studentmanagement.sms_api.dto.response;
import lombok.Data;

@Data
public class SubjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private Integer weeklyHours;
    private String teacherName;
    private String courseName;
}
