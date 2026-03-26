package com.studentmanagement.sms_api.service.impl;

import com.studentmanagement.sms_api.dto.request.TeacherRequestDto;
import com.studentmanagement.sms_api.dto.response.TeacherResponseDto;
import com.studentmanagement.sms_api.exception.DuplicateResourceException;
import com.studentmanagement.sms_api.exception.ResourceNotFoundException;
import com.studentmanagement.sms_api.mapper.TeacherMapper;
import com.studentmanagement.sms_api.model.Teacher;
import com.studentmanagement.sms_api.repository.TeacherRepository;
import com.studentmanagement.sms_api.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public TeacherResponseDto findById(Long id) {
        return teacherMapper.toResponseDto(teacherRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Teacher not found with id: " + id)
        ));
    }

    @Override
    public List<TeacherResponseDto> findAll() {
        List <Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map(teacherMapper :: toResponseDto).toList();
    }

    @Override
    @Transactional
    public TeacherResponseDto create(TeacherRequestDto dto) {
        if(teacherRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + dto.getEmail());
        }
        return teacherMapper.toResponseDto(teacherRepository.save(teacherMapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public TeacherResponseDto update(Long id, TeacherRequestDto dto) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Teacher not found with id: " + id)
        );

        if(teacherRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new DuplicateResourceException("Email already exists");
        }

        teacherMapper.updateEntityFromDto(dto, teacher);
        return teacherMapper.toResponseDto(teacherRepository.save(teacher));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!teacherRepository.existsById(id)) {
            throw new ResourceNotFoundException("Teacher not found with id: " + id);
        }
        teacherRepository.deleteById(id);
    }
}
