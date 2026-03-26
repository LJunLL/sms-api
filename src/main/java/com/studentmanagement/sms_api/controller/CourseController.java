package com.studentmanagement.sms_api.controller;

import com.studentmanagement.sms_api.dto.request.CourseRequestDto;
import com.studentmanagement.sms_api.dto.response.CourseResponseDto;
import com.studentmanagement.sms_api.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAllCourses(){
        return ResponseEntity.ok(courseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseRequestDto courseRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(courseRequestDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CourseResponseDto>  updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequestDto courseRequestDto){
        return ResponseEntity.ok(courseService.update(id, courseRequestDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id){
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
