package com.ddmspringapp.exosaver.repository;

import com.ddmspringapp.exosaver.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Course entities.
 * Extends JpaRepository to provide standard CRUD operations
 * and additional data access methods for the Course entity.

 * JpaRepository methods include capabilities for pagination and sorting.
 * This interface allows for interaction with the database layer in the application.
 */
    public interface CourseRepository extends JpaRepository<Course, Long>{}
