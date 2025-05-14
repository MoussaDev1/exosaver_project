package com.ddmspringapp.exosaver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Topic entity in the application.
 * This class is mapped to the "topic" table in the database.
 * It contains information about a specific topic including its title, description,
 * and its association with a Course entity.
 * Utilizes Lombok annotations to reduce boilerplate code.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
