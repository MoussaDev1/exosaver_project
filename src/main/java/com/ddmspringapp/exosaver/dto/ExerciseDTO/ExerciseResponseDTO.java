package com.ddmspringapp.exosaver.dto.ExerciseDTO;

import com.ddmspringapp.exosaver.model.FeynmanStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExerciseResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String solution;
    private FeynmanStatus feynmanStatus;
    private LocalDateTime nextReviewDate;
    private int feynmanSuccessCount;
}
