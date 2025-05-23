package com.ddmspringapp.exosaver.Exceptions.CourseException;


public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Course not found with id: " + id);
    }
}

