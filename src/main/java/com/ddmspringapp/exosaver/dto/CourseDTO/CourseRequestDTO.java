package com.ddmspringapp.exosaver.dto.CourseDTO;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for handling course-related request data.
 * This class is used to encapsulate and transfer course input data
 * between the client and the server or between different application layers.
 *
 * The properties include details about the course such as:
 * - title: The name of the course.
 * - description: A brief overview of what the course is about.
 * - objectives: The goals or outcomes that the course aims to achieve.
 * - themes: The topics or subject matter that the course will cover.
 */
@Data
public class CourseRequestDTO {
    private String title;
    private String description;
    private String objectives;
    private String themes;
}
