package com.studentmanagement.sms_api.mapper;

import com.studentmanagement.sms_api.dto.request.SubjectRequestDto;
import com.studentmanagement.sms_api.dto.response.SubjectResponseDto;
import com.studentmanagement.sms_api.model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    @Mapping(source = "teacher.name", target = "teacherName")
    @Mapping(source = "course.name", target = "courseName")
    SubjectResponseDto toResponseDto(Subject subject);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "course", ignore = true)
    Subject toEntity(SubjectRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "course", ignore = true)
    void updateEntityFromDto(SubjectRequestDto dto, @MappingTarget Subject subject);
}
