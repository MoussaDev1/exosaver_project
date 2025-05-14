package com.ddmspringapp.exosaver.repository;

import com.ddmspringapp.exosaver.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByCourseId(Long courseId);
    Optional<Topic> findByIdAndCourseId(Long topicId, Long courseId);

}
