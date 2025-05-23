package com.ddmspringapp.exosaver.Exceptions.ExerciceException;

import com.ddmspringapp.exosaver.Exceptions.ApiError;
import com.ddmspringapp.exosaver.Exceptions.CourseException.CourseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExerciceNotFoundHandler {

    @ExceptionHandler(ExerciseNotFoundException.class)
    public ResponseEntity<ApiError> handleCourseNotFound(ExerciseNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Exercise Not Found",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ExerciseNotInTopicException.class)
    public ResponseEntity<ApiError> handleCourseNotInTopic(ExerciseNotInTopicException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Exercise-Topic Relation",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
