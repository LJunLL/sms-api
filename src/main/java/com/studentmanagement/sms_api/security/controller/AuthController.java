package com.studentmanagement.sms_api.security.controller;
import com.studentmanagement.sms_api.security.dto.AuthResponseDto;
import com.studentmanagement.sms_api.security.dto.LoginRequestDto;
import com.studentmanagement.sms_api.security.dto.RegisterRequestDto;
import com.studentmanagement.sms_api.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticateUser(@Valid @RequestBody LoginRequestDto loginDto){
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerUser(@Valid @RequestBody RegisterRequestDto registerDto){

        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
    }
}
