package com.studentmanagement.sms_api.dto.response;
import lombok.Data;

@Data
public class SubjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private Integer weeklyHours;
    private Long teacherId;
    private String teacherName;
    private Long courseId;
    private String courseName;
}
