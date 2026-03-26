package com.studentmanagement.sms_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class StudentRequestDto {
    @NotBlank
    private String dni;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @NotNull
    private LocalDate birthday;
    @NotNull
    private LocalDate enrollmentDate;
}
