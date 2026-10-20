package com.studentmanagement.sms_api.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private String username;
    private List<String> roles;
}
