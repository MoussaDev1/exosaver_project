package com.ddmspringapp.exosaver.controller;

import com.ddmspringapp.exosaver.dto.CourseDTO.CourseRequestDTO;
import com.ddmspringapp.exosaver.dto.CourseDTO.CourseResponseDTO;
import com.ddmspringapp.exosaver.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/courses")
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CourseRequestDTO dto){
        CourseResponseDTO created = courseService.createCourse(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/courses")
    public List<CourseResponseDTO> getAllCourses(){
        return courseService.getAllCourses();
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long id){
        CourseResponseDTO course = courseService.getCoursesById(id);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseRequestDTO dto){
        CourseResponseDTO updated = courseService.updateCourse(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/course/{id}")
    public void deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
    }
}
