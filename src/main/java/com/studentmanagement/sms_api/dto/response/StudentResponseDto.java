package com.studentmanagement.sms_api.dto.response;

import java.time.LocalDate;
import lombok.Data;

@Data
public class StudentResponseDto {
    private Long id;
    private String dni;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate birthday;
    private LocalDate enrollmentDate;
}
