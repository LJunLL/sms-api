package com.studentmanagement.sms_api.security.service;

import com.studentmanagement.sms_api.exception.DuplicateResourceException;
import com.studentmanagement.sms_api.exception.ResourceNotFoundException;
import com.studentmanagement.sms_api.model.Role;
import com.studentmanagement.sms_api.model.User;
import com.studentmanagement.sms_api.repository.RoleRepository;
import com.studentmanagement.sms_api.repository.UserRepository;
import com.studentmanagement.sms_api.security.dto.AuthResponseDto;
import com.studentmanagement.sms_api.security.dto.LoginRequestDto;
import com.studentmanagement.sms_api.security.dto.RegisterRequestDto;
import com.studentmanagement.sms_api.security.jwt.JwtGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthResponseDto login(LoginRequestDto loginDto) {
        return authenticate(loginDto.getUsername(), loginDto.getPassword());
    }

    @Transactional
    public AuthResponseDto register(RegisterRequestDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername()) || userRepository.existsByEmail(registerDto.getEmail())) {
            throw new DuplicateResourceException("Username or Email already exists");
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        user.setRoles(new HashSet<>(Set.of(role)));

        userRepository.save(user);

        return authenticate(registerDto.getUsername(), registerDto.getPassword());
    }

    private AuthResponseDto authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String token = jwtGenerator.generateJwtToken(authentication);

        String name = authentication.getName();
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return new AuthResponseDto(token, name,  authorities);
    }
}
