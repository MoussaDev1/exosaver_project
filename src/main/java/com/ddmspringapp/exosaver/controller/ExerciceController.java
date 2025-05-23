package com.ddmspringapp.exosaver.controller;

import com.ddmspringapp.exosaver.dto.ExerciseDTO.ExerciseRequestDTO;
import com.ddmspringapp.exosaver.dto.ExerciseDTO.ExerciseResponseDTO;
import com.ddmspringapp.exosaver.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course/{coursId}/topic/{topicId}")
public class ExerciceController {

    @Autowired
    private ExerciseService exerciseService;


    @PostMapping("/exercices")
    public ResponseEntity<ExerciseResponseDTO> createExercice(@RequestBody ExerciseRequestDTO dto, @PathVariable Long topicId, @PathVariable Long coursId) {
        ExerciseResponseDTO created = exerciseService.createExercise(dto, topicId, coursId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/exercices")
    public ResponseEntity<List<ExerciseResponseDTO>> getAllExercises(@PathVariable Long topicId, @PathVariable Long coursId){
        List<ExerciseResponseDTO> exercises = exerciseService.getAllExercises(topicId, coursId);
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @GetMapping("/exercice/{id}")
    public ResponseEntity<ExerciseResponseDTO> getExerciseById(@PathVariable Long id, @PathVariable Long topicId, @PathVariable Long coursId){
        ExerciseResponseDTO exercise = exerciseService.getExerciseById(id, topicId, coursId);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @PutMapping("/exercice/{id}")
    public ResponseEntity<ExerciseResponseDTO> updateExercise(@RequestBody ExerciseRequestDTO dto, @PathVariable Long id, @PathVariable Long topicId, @PathVariable Long coursId) {
        ExerciseResponseDTO updated = exerciseService.updateExercise(id, dto, topicId, coursId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/exercice/{id}")
    public void deleteExercise(@PathVariable Long id, @PathVariable Long topicId, @PathVariable Long coursId) {
        exerciseService.deleteExercise(id, topicId, coursId);
    }
}
