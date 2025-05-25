package com.ddmspringapp.exosaver.Exceptions.TopicException;

public class TopicNotInCourseException extends RuntimeException {
    public TopicNotInCourseException(Long topicId, Long courseId ) {
        super("Topic not found with id: " + topicId + " and Course with : " + courseId);
    }
}
