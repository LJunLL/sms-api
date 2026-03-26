package com.studentmanagement.sms_api.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
