package com.studentmanagement.sms_api.mapper;

import com.studentmanagement.sms_api.dto.request.StudentRequestDto;
import com.studentmanagement.sms_api.dto.response.StudentResponseDto;
import com.studentmanagement.sms_api.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentResponseDto toResponseDto(Student student);
    @Mapping(target = "id", ignore = true)
    Student toEntity(StudentRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(StudentRequestDto dto, @MappingTarget Student student);
}
