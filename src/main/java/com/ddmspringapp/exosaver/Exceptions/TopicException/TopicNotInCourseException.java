package com.ddmspringapp.exosaver.Exceptions.TopicException;

public class TopicNotInCourseException extends RuntimeException {
    public TopicNotInCourseException(Long topicId, Long courseId ) {
        super("Course not found with id: " + courseId + " and topic: " + topicId);
    }
}
