package com.ddmspringapp.exosaver.Exceptions.CourseException;

import com.ddmspringapp.exosaver.Exceptions.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CourseExceptionHandler {

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ApiError> handleCourseNotFound(CourseNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Course Not Found",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
