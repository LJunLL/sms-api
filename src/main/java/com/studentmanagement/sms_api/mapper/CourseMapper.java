package com.studentmanagement.sms_api.mapper;
import com.studentmanagement.sms_api.dto.request.CourseRequestDto;
import com.studentmanagement.sms_api.dto.response.CourseResponseDto;
import com.studentmanagement.sms_api.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseResponseDto toResponseDto(Course course);
    @Mapping(target = "id", ignore = true)
    Course toEntity(CourseRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CourseRequestDto dto, @MappingTarget Course course);
}
