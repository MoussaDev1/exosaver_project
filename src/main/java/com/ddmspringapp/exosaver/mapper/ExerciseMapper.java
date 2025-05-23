package com.ddmspringapp.exosaver.mapper;

import com.ddmspringapp.exosaver.dto.ExerciseDTO.ExerciseRequestDTO;
import com.ddmspringapp.exosaver.dto.ExerciseDTO.ExerciseResponseDTO;
import com.ddmspringapp.exosaver.model.Exercise;

public class ExerciseMapper {
    public static ExerciseResponseDTO toResponseDTO(Exercise exercise){
        ExerciseResponseDTO dto = new ExerciseResponseDTO();
        dto.setId(exercise.getId());
        dto.setTitle(exercise.getTitle());
        dto.setDescription(exercise.getDescription());
        dto.setSolution(exercise.getSolution());
        return dto;
    }

    public static Exercise toEntity(ExerciseRequestDTO dto){
        Exercise exercise = new Exercise();
        exercise.setTitle(dto.getTitle());
        exercise.setSolution(dto.getSolution());
        exercise.setDescription(dto.getDescription());
        return exercise;
    }
}
