package com.ddmspringapp.exosaver.dto.ExerciseDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExerciseResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String Solution;
    private String FeynmanStatus;
    private LocalDateTime nextReviewDate;
    private int feynmanSuccessCount;
}
