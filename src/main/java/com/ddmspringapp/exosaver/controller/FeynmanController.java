package com.ddmspringapp.exosaver.controller;

import com.ddmspringapp.exosaver.dto.ExerciseDTO.FeynmanEvaluationRequestDTO;
import com.ddmspringapp.exosaver.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course/{coursId}/topic/{topicId}")
public class FeynmanController {

    @Autowired
    private ExerciseService exerciseService;

    @PutMapping("/exercice/{id}/feynman")
    public ResponseEntity<Void> updateFeynmanStatus(@PathVariable Long id, @PathVariable Long topicId, @PathVariable Long coursId , @RequestBody FeynmanEvaluationRequestDTO dto){
        exerciseService.UpdateFeynmanStatus(id, topicId, coursId, dto.getFeynmanStatus());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
