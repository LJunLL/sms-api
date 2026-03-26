package com.studentmanagement.sms_api.controller;

import com.studentmanagement.sms_api.dto.request.SubjectRequestDto;
import com.studentmanagement.sms_api.dto.response.SubjectResponseDto;
import com.studentmanagement.sms_api.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectResponseDto>> getAllSubjects(){
        return  ResponseEntity.ok(subjectService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDto> getSubjectById(@PathVariable Long id){
        return ResponseEntity.ok(subjectService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SubjectResponseDto> createSubject(@Valid @RequestBody SubjectRequestDto subjectRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.create(subjectRequestDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SubjectResponseDto>  updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectRequestDto subjectRequestDto){
        return ResponseEntity.ok(subjectService.update(id, subjectRequestDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id){
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<SubjectResponseDto>> getSubjectsByCourse(@PathVariable Long courseId){
        return ResponseEntity.ok(subjectService.findByCourseId(courseId));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<SubjectResponseDto>> getSubjectsByTeacher(@PathVariable Long teacherId){
        return ResponseEntity.ok(subjectService.findByTeacherId(teacherId));
    }
}
