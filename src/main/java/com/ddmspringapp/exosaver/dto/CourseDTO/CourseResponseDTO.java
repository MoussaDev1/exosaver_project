package com.ddmspringapp.exosaver.dto.CourseDTO;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for handling course-related response data.
 * This class is used to encapsulate and transfer course output data
 * from the server to the client or between different application layers.
 *
 * The properties include:
 * - id: The unique identifier of the course.
 * - title: The name of the course.
 * - description: A brief overview of the course.
 * - objectives: The goals or intended outcomes of the course.
 * - themes: The topics or subject matter covered in the course.
 */
@Data
public class CourseResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String objectives;
    private String themes;
}
