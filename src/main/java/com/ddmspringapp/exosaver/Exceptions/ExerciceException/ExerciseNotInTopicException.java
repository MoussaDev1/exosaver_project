package com.ddmspringapp.exosaver.Exceptions.ExerciceException;

public class ExerciseNotInTopicException extends RuntimeException {
  public ExerciseNotInTopicException(Long topicId, Long exerciseId) {
    super("Exercise not found with id: " + exerciseId + " and topic: " + topicId);
  }
}
