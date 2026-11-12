package com.studentmanagement.sms_api.mapper;

import com.studentmanagement.sms_api.dto.request.EnrollmentRequestDto;
import com.studentmanagement.sms_api.dto.request.SubjectRequestDto;
import com.studentmanagement.sms_api.dto.response.EnrollmentResponseDto;
import com.studentmanagement.sms_api.model.Enrollment;
import com.studentmanagement.sms_api.model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    @Mapping(target = "studentName", expression = "java(enrollment.getStudent().getName() + \" \" + enrollment.getStudent().getLastName())")
    @Mapping(source = "subject.name", target = "subjectName")
    EnrollmentResponseDto toResponseDto(Enrollment enrollment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "status", ignore = true)
    Enrollment toEntity(EnrollmentRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "grade", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntityFromDto(EnrollmentRequestDto dto, @MappingTarget Enrollment enrollment);
}
