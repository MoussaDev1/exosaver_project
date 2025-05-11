package com.ddmspringapp.exosaver.mapper;

import com.ddmspringapp.exosaver.dto.CourseDTO.CourseRequestDTO;
import com.ddmspringapp.exosaver.dto.CourseDTO.CourseResponseDTO;
import com.ddmspringapp.exosaver.model.Course;

public class CourseMapper {
    public static CourseResponseDTO toResponseDTO(Course course) {
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setObjectives(course.getObjectives());
        dto.setThemes(course.getThemes());
        return dto;
    }

    public static Course toEntity(CourseRequestDTO dto){
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setObjectives(dto.getObjectives());
        course.setThemes(dto.getThemes());
        return course;
    }
}
