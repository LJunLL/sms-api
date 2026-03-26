package com.studentmanagement.sms_api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = "password123";
        String encodePassword = passwordEncoder.encode(password);
        System.out.println(encodePassword);
    }
}
