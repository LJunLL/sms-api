package com.studentmanagement.sms_api.service.impl;

import com.studentmanagement.sms_api.dto.request.SubjectRequestDto;
import com.studentmanagement.sms_api.dto.response.SubjectResponseDto;
import com.studentmanagement.sms_api.exception.DuplicateResourceException;
import com.studentmanagement.sms_api.exception.ResourceNotFoundException;
import com.studentmanagement.sms_api.mapper.SubjectMapper;
import com.studentmanagement.sms_api.model.Course;
import com.studentmanagement.sms_api.model.Subject;
import com.studentmanagement.sms_api.model.Teacher;
import com.studentmanagement.sms_api.repository.CourseRepository;
import com.studentmanagement.sms_api.repository.SubjectRepository;
import com.studentmanagement.sms_api.repository.TeacherRepository;
import com.studentmanagement.sms_api.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    private final SubjectMapper subjectMapper;

    @Override
    public SubjectResponseDto findById(Long id) {
        return subjectMapper.toResponseDto(subjectRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Subject not found with id " + id)
        ));
    }

    @Override
    public List<SubjectResponseDto> findAll() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream().map(subjectMapper :: toResponseDto).toList();
    }

    @Override
    public List<SubjectResponseDto> findByCourseId(Long courseId) {
        List<Subject> subjects = subjectRepository.findByCourseId(courseId);
        return subjects.stream().map(subjectMapper :: toResponseDto).toList();
    }

    @Override
    public List<SubjectResponseDto> findByTeacherId(Long teacherId) {
        List<Subject> subjects = subjectRepository.findByTeacherId(teacherId);
        return subjects.stream().map(subjectMapper :: toResponseDto).toList();
    }

    @Override
    @Transactional
    public SubjectResponseDto create(SubjectRequestDto dto) {
        Teacher teacher = teacherRepository.findById(dto.getTeacherId()).orElseThrow(
                () -> new ResourceNotFoundException("Teacher not found with id " + dto.getTeacherId())
        );

        Course course = courseRepository.findById(dto.getCourseId()).orElseThrow(
                () -> new ResourceNotFoundException("Course not found with id " + dto.getCourseId())
        );

        if(subjectRepository.existsByNameAndCourseId(dto.getName(),dto.getCourseId())){
            throw new DuplicateResourceException("Subject '" + dto.getName() + "' already exists in this course");
        }

        Subject subject = subjectMapper.toEntity(dto);
        subject.setTeacher(teacher);
        subject.setCourse(course);
        return  subjectMapper.toResponseDto(subjectRepository.save(subject));
    }

    @Override
    @Transactional
    public SubjectResponseDto update(Long id, SubjectRequestDto dto) {
        Subject subject =  subjectRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Subject not found with id " + id)
        );

        Teacher teacher = teacherRepository.findById(dto.getTeacherId()).orElseThrow(
                () -> new ResourceNotFoundException("Teacher not found with id " + dto.getTeacherId())
        );

        Course course = courseRepository.findById(dto.getCourseId()).orElseThrow(
                () -> new ResourceNotFoundException("Course not found with id " + dto.getCourseId())
        );

        if(subjectRepository.existsByNameAndCourseIdAndIdNot(dto.getName(),dto.getCourseId(),id)){
            throw new DuplicateResourceException("Subject '" + dto.getName() + "' already exists in this course");
        }

        subjectMapper.updateEntityFromDto(dto, subject);
        subject.setTeacher(teacher);
        subject.setCourse(course);
        return subjectMapper.toResponseDto(subjectRepository.save(subject));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!subjectRepository.existsById(id)){
            throw new ResourceNotFoundException("Subject not found with id " + id);
        }
        subjectRepository.deleteById(id);
    }
}
