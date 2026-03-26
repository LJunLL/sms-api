package com.studentmanagement.sms_api.service.impl;

import com.studentmanagement.sms_api.dto.request.StudentRequestDto;
import com.studentmanagement.sms_api.dto.response.StudentResponseDto;
import com.studentmanagement.sms_api.exception.DuplicateResourceException;
import com.studentmanagement.sms_api.exception.ResourceNotFoundException;
import com.studentmanagement.sms_api.mapper.StudentMapper;
import com.studentmanagement.sms_api.model.Student;
import com.studentmanagement.sms_api.repository.StudentRepository;
import com.studentmanagement.sms_api.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentResponseDto findById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student not found with id: " + id)
        );
        return studentMapper.toResponseDto(student);
    }

    @Override
    public List<StudentResponseDto> findAll() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(studentMapper::toResponseDto).toList();
    }

    @Override
    @Transactional
    public StudentResponseDto create(StudentRequestDto dto) {
        if(studentRepository.existsByDni(dto.getDni()) || studentRepository.existsByEmail(dto.getEmail()) ){
            throw new DuplicateResourceException("DNI or Email already exists");
        }
        return studentMapper.toResponseDto(studentRepository.save(studentMapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public StudentResponseDto update(Long id, StudentRequestDto dto) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student not found with id: " + id)
        );

        if(studentRepository.existsByEmailAndIdNot(dto.getEmail(), id)){
            throw new DuplicateResourceException("Email already exists: "  + dto.getEmail());
        }
        if(studentRepository.existsByDniAndIdNot(dto.getDni(), id)){
            throw new DuplicateResourceException("DNI already exists: " +  dto.getDni());
        }
        studentMapper.updateEntityFromDto(dto, student);
        return studentMapper.toResponseDto(studentRepository.save(student));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!studentRepository.existsById(id)){
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }
}
