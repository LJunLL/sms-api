package com.studentmanagement.sms_api.service.impl;

import com.studentmanagement.sms_api.dto.request.CourseRequestDto;
import com.studentmanagement.sms_api.dto.response.CourseResponseDto;
import com.studentmanagement.sms_api.exception.DuplicateResourceException;
import com.studentmanagement.sms_api.exception.ResourceNotFoundException;
import com.studentmanagement.sms_api.mapper.CourseMapper;
import com.studentmanagement.sms_api.model.Course;
import com.studentmanagement.sms_api.repository.CourseRepository;
import com.studentmanagement.sms_api.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseResponseDto findById(Long id) {
        Course course =  courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Course not found with id: " + id)
        );
        return courseMapper.toResponseDto(course);
    }

    @Override
    public List<CourseResponseDto> findAll() {
        List <Course> courses = courseRepository.findAll();
        return courses.stream().map(courseMapper::toResponseDto).toList();
    }

    @Override
    @Transactional
    public CourseResponseDto create(CourseRequestDto dto) {
        if(courseRepository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("Course already exists by name " + dto.getName());
        }
        return courseMapper.toResponseDto(courseRepository.save(courseMapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public CourseResponseDto update(Long id, CourseRequestDto dto) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Course not found with id: " + id)
        );

        if(courseRepository.existsByNameAndIdNot(dto.getName(), id)) {
            throw new DuplicateResourceException("Course already exists by name " + dto.getName());
        }

        courseMapper.updateEntityFromDto(dto, course);
        return courseMapper.toResponseDto(courseRepository.save(course));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with  id: " + id);
        }
        courseRepository.deleteById(id);
    }
}
