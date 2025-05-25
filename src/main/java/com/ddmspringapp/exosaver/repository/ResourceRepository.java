package com.ddmspringapp.exosaver.repository;

import com.ddmspringapp.exosaver.model.Course;
import com.ddmspringapp.exosaver.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByCourseId(Long courseId);
    List<Resource> findByExerciseId(Long topicId);

}
