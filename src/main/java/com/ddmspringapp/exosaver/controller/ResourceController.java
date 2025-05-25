package com.ddmspringapp.exosaver.controller;

import com.ddmspringapp.exosaver.dto.ResourceDTO.ResourceRequestDTO;
import com.ddmspringapp.exosaver.dto.ResourceDTO.ResourceResponseDTO;
import com.ddmspringapp.exosaver.service.ResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
// Le contrôleur gère les ressources associées aux cours et aux exercices
// Il permet de créer, récupérer, mettre à jour et supprimer des ressources
// Les ressources peuvent être associées à un cours ou à un exercice spécifique
// Les ressources sont généralement des fichiers ou des liens utiles pour les étudiants
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    // Créer une ressource pour un cours
    @PostMapping("/course/{courseId}/resources")
    public ResponseEntity<ResourceResponseDTO> createForCourse(@PathVariable Long courseId, @RequestBody ResourceRequestDTO dto) {
        dto.setCourseId(courseId);
        ResourceResponseDTO response = resourceService.createResource(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Créer une ressource pour un exercice
    @PostMapping("/course/{courseId}/topics/{topicId}/exercises/{exerciseId}/resources")
    public ResponseEntity<ResourceResponseDTO> createForExercise(@PathVariable Long exerciseId, @RequestBody ResourceRequestDTO dto) {
        dto.setExerciseId(exerciseId);
        ResourceResponseDTO response = resourceService.createResource(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Récupérer toutes les ressources d'un cours
    @GetMapping("/course/{courseId}/resources")
    public ResponseEntity<List<ResourceResponseDTO>> getAllByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(resourceService.getAllResources(courseId, null));
    }

    // Récupérer toutes les ressources d'un exercice
    @GetMapping("/course/{courseId}/topics/{topicId}/exercises/{exerciseId}/resources")
    public ResponseEntity<List<ResourceResponseDTO>> getAllByExercise(@PathVariable Long exerciseId) {
        return ResponseEntity.ok(resourceService.getAllResources(null, exerciseId));
    }

    // Récupérer une ressource par ID
    @GetMapping("/resource/{id}")
    public ResponseEntity<ResourceResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(resourceService.getResourceById(id));
    }

    // Mettre à jour une ressource
    @PutMapping("/resource/{id}")
    public ResponseEntity<ResourceResponseDTO> update(@PathVariable Long id, @RequestBody ResourceRequestDTO dto) {
        return ResponseEntity.ok(resourceService.updateResource(id, dto));
    }

    // Supprimer une ressource
    @DeleteMapping("/resource/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}
