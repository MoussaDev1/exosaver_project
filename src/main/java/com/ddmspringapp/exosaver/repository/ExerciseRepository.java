package com.ddmspringapp.exosaver.repository;

import com.ddmspringapp.exosaver.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByIdAndTopicId(Long exerciseId, Long topicId);
    List<Exercise> findByTopicId(Long topicId);
}
