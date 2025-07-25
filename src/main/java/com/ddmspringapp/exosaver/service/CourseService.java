package com.ddmspringapp.exosaver.service;

import com.ddmspringapp.exosaver.Exceptions.CourseException.CourseNotFoundException;
import com.ddmspringapp.exosaver.dto.CourseDTO.CourseRequestDTO;
import com.ddmspringapp.exosaver.dto.CourseDTO.CourseResponseDTO;
import com.ddmspringapp.exosaver.mapper.CourseMapper;
import com.ddmspringapp.exosaver.model.Course;
import com.ddmspringapp.exosaver.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing courses within the application.
 * Provides business logic for creating, retrieving, updating, and deleting courses.
 * Utilizes CourseRepository for persistence and leverages mapping between entities
 * and DTOs through CourseMapper to facilitate interaction with external layers.
 */
@Service
public class CourseService {

    @Autowired
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseResponseDTO createCourse(CourseRequestDTO dto){
        Course course = CourseMapper.toEntity(dto);
        course = courseRepository.save(course);
        return CourseMapper.toResponseDTO(course);
    }

    public CourseResponseDTO getCoursesById(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return CourseMapper.toResponseDTO(course);
    }

    public List<CourseResponseDTO> getAllCourses(){
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(CourseMapper::toResponseDTO).toList();
    }

    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO dto){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setObjectives(dto.getObjectives());
        course.setThemes(dto.getThemes());
        Course updatedCourse = courseRepository.save(course);
        return CourseMapper.toResponseDTO(updatedCourse);
    }

    public void deleteCourse(Long id){
        if(!courseRepository.existsById(id)){
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }
}
