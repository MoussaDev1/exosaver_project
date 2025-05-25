package com.ddmspringapp.exosaver.service;

import com.ddmspringapp.exosaver.Exceptions.CourseException.CourseNotFoundException;
import com.ddmspringapp.exosaver.Exceptions.ExerciceException.ExerciseNotFoundException;
import com.ddmspringapp.exosaver.Exceptions.ResourceException.ResourceNotFoundException;
import com.ddmspringapp.exosaver.Exceptions.TopicException.TopicNotFoundException;
import com.ddmspringapp.exosaver.dto.ResourceDTO.ResourceRequestDTO;
import com.ddmspringapp.exosaver.dto.ResourceDTO.ResourceResponseDTO;
import com.ddmspringapp.exosaver.mapper.ResourceMapper;
import com.ddmspringapp.exosaver.model.Course;
import com.ddmspringapp.exosaver.model.Exercise;
import com.ddmspringapp.exosaver.model.Resource;
import com.ddmspringapp.exosaver.repository.CourseRepository;
import com.ddmspringapp.exosaver.repository.ExerciseRepository;
import com.ddmspringapp.exosaver.repository.ResourceRepository;
import com.ddmspringapp.exosaver.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class ResourceService {

    private final ResourceRepository resourceRepository;
    private final ExerciseRepository exerciseRepository;
    private final CourseRepository courseRepository;

    public ResourceService(ResourceRepository resourceRepository, ExerciseRepository exerciseRepository, CourseRepository courseRepository) {
        this.resourceRepository = resourceRepository;
        this.exerciseRepository = exerciseRepository;
        this.courseRepository = courseRepository;
    }

    public ResourceResponseDTO createResource(ResourceRequestDTO dto) {
        boolean courseSet = dto.getCourseId() != null;
        boolean exerciseSet = dto.getExerciseId() != null;

        if (courseSet && exerciseSet) {
            throw new IllegalArgumentException("Une ressource ne peut être liée qu'à un seul parent (Course OU Exercise).");
        }
        Resource resource = ResourceMapper.toEntity(dto);

        if (courseSet) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new CourseNotFoundException(dto.getCourseId()));
            resource.setCourse(course);
        } else if (exerciseSet) {
            Exercise exercise = exerciseRepository.findById(dto.getExerciseId())
                    .orElseThrow(() -> new ExerciseNotFoundException(dto.getExerciseId()));
            resource.setExercise(exercise);
        } else {
            throw new IllegalArgumentException("Une ressource doit être liée à un Course ou un Exercise.");
        }
        resource = resourceRepository.save(resource);
        return ResourceMapper.toResponseDTO(resource);
    }

    public ResourceResponseDTO getResourceById(Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException(resourceId));
        return ResourceMapper.toResponseDTO(resource);
    }

    public List<ResourceResponseDTO> getAllResources(Long courseId, Long exerciseId) {
        if (courseId != null && exerciseId != null) {
            throw new IllegalArgumentException("Spécifie soit un courseId, soit un exerciseId, pas les deux.");
        }
        List<Resource> resources;
        if (courseId != null) {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new CourseNotFoundException(courseId));
            resources = resourceRepository.findByCourseId(courseId);
        } else if (exerciseId != null) {
            Exercise exercise = exerciseRepository.findById(exerciseId)
                    .orElseThrow(() -> new ExerciseNotFoundException(exerciseId));
            resources = resourceRepository.findByExerciseId(exerciseId);
        } else {
            throw new IllegalArgumentException("Il faut spécifier un courseId ou un exerciseId.");
        }

        return resources.stream()
                .map(ResourceMapper::toResponseDTO)
                .toList();
    }

    public ResourceResponseDTO updateResource(Long resourceId, ResourceRequestDTO dto) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException(resourceId));

        resource.setType(dto.getType());
        resource.setUrl(dto.getUrl());

        boolean courseSet = dto.getCourseId() != null;
        boolean exerciseSet = dto.getExerciseId() != null;

        if (courseSet && exerciseSet) {
            throw new IllegalArgumentException("Une ressource ne peut être liée qu'à un seul parent (Course OU Exercise).");
        }

        if (courseSet) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new CourseNotFoundException(dto.getCourseId()));
            resource.setCourse(course);
            resource.setExercise(null); // reset si changement
        } else if (exerciseSet) {
            Exercise exercise = exerciseRepository.findById(dto.getExerciseId())
                    .orElseThrow(() -> new ExerciseNotFoundException(dto.getExerciseId()));
            resource.setExercise(exercise);
            resource.setCourse(null); // reset si changement
        } else {
            throw new IllegalArgumentException("Une ressource doit être liée à un Course ou un Exercise.");
        }

        resource = resourceRepository.save(resource);
        return ResourceMapper.toResponseDTO(resource);
    }

    public void deleteResource(Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException(resourceId));
        resourceRepository.delete(resource);
    }
}
