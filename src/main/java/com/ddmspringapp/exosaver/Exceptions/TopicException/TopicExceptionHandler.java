package com.ddmspringapp.exosaver.Exceptions.TopicException;

import com.ddmspringapp.exosaver.Exceptions.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class TopicExceptionHandler {

    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<ApiError> handleTopicNotFound(TopicNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Topic Not Found",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TopicNotInCourseException.class)
    public ResponseEntity<ApiError> handleTopicNotInCourse(TopicNotInCourseException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Topic-Course Relation",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
