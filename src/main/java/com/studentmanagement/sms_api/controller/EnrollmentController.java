package com.studentmanagement.sms_api.controller;

import com.studentmanagement.sms_api.dto.request.EnrollmentRequestDto;
import com.studentmanagement.sms_api.dto.response.EnrollmentResponseDto;
import com.studentmanagement.sms_api.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponseDto>getEnrollment(@PathVariable Long id){
        return ResponseEntity.ok(enrollmentService.findById(id));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentByStudentId(@PathVariable Long id){
        return ResponseEntity.ok(enrollmentService.findByStudentId(id));
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentBySubjectId(@PathVariable Long id){
        return ResponseEntity.ok(enrollmentService.findBySubjectId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EnrollmentResponseDto> createEnrollment(@Valid @RequestBody EnrollmentRequestDto enrollmentRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.enroll(enrollmentRequestDto));
    }

    @PutMapping("/{id}/grade")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EnrollmentResponseDto> updateGrade(@PathVariable Long id, @RequestParam Double grade){
        return ResponseEntity.ok(enrollmentService.updateGrade(id, grade));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void>  deleteEnrollment(@PathVariable Long id){
        enrollmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
