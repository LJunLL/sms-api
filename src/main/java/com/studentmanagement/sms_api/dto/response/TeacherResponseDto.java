package com.studentmanagement.sms_api.dto.response;
import lombok.Data;

@Data
public class TeacherResponseDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String specialty;
}
