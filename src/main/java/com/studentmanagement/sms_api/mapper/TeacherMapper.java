package com.studentmanagement.sms_api.mapper;

import com.studentmanagement.sms_api.dto.request.TeacherRequestDto;
import com.studentmanagement.sms_api.dto.response.TeacherResponseDto;
import com.studentmanagement.sms_api.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherResponseDto toResponseDto(Teacher teacher);
    @Mapping(target = "id",  ignore = true)
    Teacher toEntity(TeacherRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(TeacherRequestDto dto, @MappingTarget Teacher teacher);
}
