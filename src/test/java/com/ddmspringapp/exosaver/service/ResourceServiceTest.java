package com.ddmspringapp.exosaver.service;

import com.ddmspringapp.exosaver.Exceptions.CourseException.CourseNotFoundException;
import com.ddmspringapp.exosaver.Exceptions.ExerciceException.ExerciseNotFoundException;
import com.ddmspringapp.exosaver.Exceptions.ResourceException.ResourceNotFoundException;
import com.ddmspringapp.exosaver.dto.ResourceDTO.ResourceRequestDTO;
import com.ddmspringapp.exosaver.dto.ResourceDTO.ResourceResponseDTO;
import com.ddmspringapp.exosaver.mapper.ResourceMapper;
import com.ddmspringapp.exosaver.model.Course;
import com.ddmspringapp.exosaver.model.Exercise;
import com.ddmspringapp.exosaver.model.Resource;
import com.ddmspringapp.exosaver.repository.CourseRepository;
import com.ddmspringapp.exosaver.repository.ExerciseRepository;
import com.ddmspringapp.exosaver.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResourceServiceTest {

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private ResourceService resourceService;

    private Course course;
    private Exercise exercise;
    private Resource resource;
    private ResourceRequestDTO resourceRequestDTO;
    private ResourceResponseDTO resourceResponseDTO;
    private Long resourceId;
    private Long courseId;
    private Long exerciseId;

    @BeforeEach
    void setUp() {
        resourceId = 1L;
        courseId = 1L;
        exerciseId = 1L;

        course = new Course();
        course.setId(courseId);
        course.setTitle("Cours de test");

        exercise = new Exercise();
        exercise.setId(exerciseId);
        exercise.setTitle("Exercice de test");

        resource = new Resource();
        resource.setId(resourceId);
        resource.setType("PDF");
        resource.setUrl("https://example.com/resource.pdf");
        resource.setCourse(course);

        resourceRequestDTO = new ResourceRequestDTO();
        resourceRequestDTO.setType("PDF");
        resourceRequestDTO.setUrl("https://example.com/resource.pdf");
        resourceRequestDTO.setCourseId(courseId);

        resourceResponseDTO = new ResourceResponseDTO();
        resourceResponseDTO.setId(resourceId);
        resourceResponseDTO.setType("PDF");
        resourceResponseDTO.setUrl("https://example.com/resource.pdf");
        resourceResponseDTO.setCourseId(courseId);
    }

    /**
     * Vérifie que la création d'une ressource liée à un cours fonctionne correctement
     */
    @Test
    void createResource_WithCourseId_ShouldReturnResourceResponseDTO() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);

        // Act
        ResourceResponseDTO result = resourceService.createResource(resourceRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(resourceId, result.getId());
        assertEquals(resourceRequestDTO.getType(), result.getType());
        assertEquals(resourceRequestDTO.getUrl(), result.getUrl());
        assertEquals(courseId, result.getCourseId());
        assertNull(result.getExerciseId());
        verify(courseRepository).findById(courseId);
        verify(resourceRepository).save(any(Resource.class));
        verify(exerciseRepository, never()).findById(anyLong());
    }

    /**
     * Vérifie que la création d'une ressource liée à un exercice fonctionne correctement
     */
    @Test
    void createResource_WithExerciseId_ShouldReturnResourceResponseDTO() {
        // Arrange
        resourceRequestDTO.setCourseId(null);
        resourceRequestDTO.setExerciseId(exerciseId);

        Resource exerciseResource = new Resource();
        exerciseResource.setId(resourceId);
        exerciseResource.setType("PDF");
        exerciseResource.setUrl("https://example.com/resource.pdf");
        exerciseResource.setExercise(exercise);

        ResourceResponseDTO exerciseResourceDTO = new ResourceResponseDTO();
        exerciseResourceDTO.setId(resourceId);
        exerciseResourceDTO.setType("PDF");
        exerciseResourceDTO.setUrl("https://example.com/resource.pdf");
        exerciseResourceDTO.setExerciseId(exerciseId);

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));
        when(resourceRepository.save(any(Resource.class))).thenReturn(exerciseResource);

        // Act
        ResourceResponseDTO result = resourceService.createResource(resourceRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(resourceId, result.getId());
        assertEquals(resourceRequestDTO.getType(), result.getType());
        assertEquals(resourceRequestDTO.getUrl(), result.getUrl());
        assertEquals(exerciseId, result.getExerciseId());
        assertNull(result.getCourseId());
        verify(exerciseRepository).findById(exerciseId);
        verify(resourceRepository).save(any(Resource.class));
        verify(courseRepository, never()).findById(anyLong());
    }

    /**
     * Vérifie qu'une exception est levée quand on tente de créer une ressource sans lien
     */
    @Test
    void createResource_WithoutParent_ShouldThrowIllegalArgumentException() {
        // Arrange
        resourceRequestDTO.setCourseId(null);
        resourceRequestDTO.setExerciseId(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            resourceService.createResource(resourceRequestDTO);
        });

        assertEquals("Une ressource doit être liée à un Course ou un Exercise.", exception.getMessage());
        verify(courseRepository, never()).findById(anyLong());
        verify(exerciseRepository, never()).findById(anyLong());
        verify(resourceRepository, never()).save(any(Resource.class));
    }

    /**
     * Vérifie qu'une exception est levée quand on tente de créer une ressource avec deux liens
     */
    @Test
    void createResource_WithBothParents_ShouldThrowIllegalArgumentException() {
        // Arrange
        resourceRequestDTO.setCourseId(courseId);
        resourceRequestDTO.setExerciseId(exerciseId);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            resourceService.createResource(resourceRequestDTO);
        });

        assertEquals("Une ressource ne peut être liée qu'à un seul parent (Course OU Exercise).", exception.getMessage());
        verify(courseRepository, never()).findById(anyLong());
        verify(exerciseRepository, never()).findById(anyLong());
        verify(resourceRepository, never()).save(any(Resource.class));
    }

    /**
     * Vérifie qu'une exception est levée quand le cours n'existe pas lors de la création d'une ressource
     */
    @Test
    void createResource_WithInvalidCourseId_ShouldThrowCourseNotFoundException() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CourseNotFoundException.class, () -> {
            resourceService.createResource(resourceRequestDTO);
        });
        verify(courseRepository).findById(courseId);
        verify(resourceRepository, never()).save(any(Resource.class));
    }

    /**
     * Vérifie qu'une exception est levée quand l'exercice n'existe pas lors de la création d'une ressource
     */
    @Test
    void createResource_WithInvalidExerciseId_ShouldThrowExerciseNotFoundException() {
        // Arrange
        resourceRequestDTO.setCourseId(null);
        resourceRequestDTO.setExerciseId(exerciseId);
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ExerciseNotFoundException.class, () -> {
            resourceService.createResource(resourceRequestDTO);
        });
        verify(exerciseRepository).findById(exerciseId);
        verify(resourceRepository, never()).save(any(Resource.class));
    }

    /**
     * Vérifie que la récupération d'une ressource par ID fonctionne correctement
     */
    @Test
    void getResourceById_WithValidId_ShouldReturnResourceResponseDTO() {
        // Arrange
        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));

        // Act
        ResourceResponseDTO result = resourceService.getResourceById(resourceId);

        // Assert
        assertNotNull(result);
        assertEquals(resourceId, result.getId());
        assertEquals(resource.getType(), result.getType());
        assertEquals(resource.getUrl(), result.getUrl());
        verify(resourceRepository).findById(resourceId);
    }

    /**
     * Vérifie qu'une exception est levée quand la ressource n'existe pas
     */
    @Test
    void getResourceById_WithInvalidId_ShouldThrowResourceNotFoundException() {
        // Arrange
        when(resourceRepository.findById(resourceId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            resourceService.getResourceById(resourceId);
        });
        verify(resourceRepository).findById(resourceId);
    }

    /**
     * Vérifie que la récupération de toutes les ressources d'un cours fonctionne correctement
     */
    @Test
    void getAllResources_WithCourseId_ShouldReturnListOfResourceResponseDTOs() {
        // Arrange
        Resource resource2 = new Resource();
        resource2.setId(2L);
        resource2.setType("VIDEO");
        resource2.setUrl("https://example.com/video.mp4");
        resource2.setCourse(course);

        List<Resource> resources = Arrays.asList(resource, resource2);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(resourceRepository.findByCourseId(courseId)).thenReturn(resources);

        // Act
        List<ResourceResponseDTO> result = resourceService.getAllResources(courseId, null);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(resourceId, result.get(0).getId());
        assertEquals("PDF", result.get(0).getType());
        assertEquals(2L, result.get(1).getId());
        assertEquals("VIDEO", result.get(1).getType());
        verify(courseRepository).findById(courseId);
        verify(resourceRepository).findByCourseId(courseId);
    }

    /**
     * Vérifie que la récupération de toutes les ressources d'un exercice fonctionne correctement
     */
    @Test
    void getAllResources_WithExerciseId_ShouldReturnListOfResourceResponseDTOs() {
        // Arrange
        Resource resource1 = new Resource();
        resource1.setId(1L);
        resource1.setType("PDF");
        resource1.setUrl("https://example.com/resource.pdf");
        resource1.setExercise(exercise);

        Resource resource2 = new Resource();
        resource2.setId(2L);
        resource2.setType("VIDEO");
        resource2.setUrl("https://example.com/video.mp4");
        resource2.setExercise(exercise);

        List<Resource> resources = Arrays.asList(resource1, resource2);

        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));
        when(resourceRepository.findByExerciseId(exerciseId)).thenReturn(resources);

        // Act
        List<ResourceResponseDTO> result = resourceService.getAllResources(null, exerciseId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("PDF", result.get(0).getType());
        assertEquals(2L, result.get(1).getId());
        assertEquals("VIDEO", result.get(1).getType());
        verify(exerciseRepository).findById(exerciseId);
        verify(resourceRepository).findByExerciseId(exerciseId);
    }

    /**
     * Vérifie qu'une exception est levée quand on ne spécifie ni courseId ni exerciseId
     */
    @Test
    void getAllResources_WithNoIds_ShouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            resourceService.getAllResources(null, null);
        });

        assertEquals("Il faut spécifier un courseId ou un exerciseId.", exception.getMessage());
        verify(courseRepository, never()).findById(anyLong());
        verify(exerciseRepository, never()).findById(anyLong());
    }

    /**
     * Vérifie qu'une exception est levée quand on spécifie les deux IDs
     */
    @Test
    void getAllResources_WithBothIds_ShouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            resourceService.getAllResources(courseId, exerciseId);
        });

        assertEquals("Spécifie soit un courseId, soit un exerciseId, pas les deux.", exception.getMessage());
        verify(courseRepository, never()).findById(anyLong());
        verify(exerciseRepository, never()).findById(anyLong());
    }

    /**
     * Vérifie qu'une exception est levée quand le cours n'existe pas
     */
    @Test
    void getAllResources_WithInvalidCourseId_ShouldThrowCourseNotFoundException() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CourseNotFoundException.class, () -> {
            resourceService.getAllResources(courseId, null);
        });
        verify(courseRepository).findById(courseId);
        verify(resourceRepository, never()).findByCourseId(anyLong());
    }

    /**
     * Vérifie qu'une exception est levée quand l'exercice n'existe pas
     */
    @Test
    void getAllResources_WithInvalidExerciseId_ShouldThrowExerciseNotFoundException() {
        // Arrange
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ExerciseNotFoundException.class, () -> {
            resourceService.getAllResources(null, exerciseId);
        });
        verify(exerciseRepository).findById(exerciseId);
        verify(resourceRepository, never()).findByExerciseId(anyLong());
    }

    /**
     * Vérifie que la mise à jour d'une ressource fonctionne correctement avec un cours
     */
    @Test
    void updateResource_WithCourseId_ShouldReturnUpdatedResourceResponseDTO() {
        // Arrange
        ResourceRequestDTO updateDTO = new ResourceRequestDTO();
        updateDTO.setType("VIDEO");
        updateDTO.setUrl("https://example.com/updated.mp4");
        updateDTO.setCourseId(courseId);

        Resource existingResource = new Resource();
        existingResource.setId(resourceId);
        existingResource.setType("PDF");
        existingResource.setUrl("https://example.com/resource.pdf");

        Resource updatedResource = new Resource();
        updatedResource.setId(resourceId);
        updatedResource.setType("VIDEO");
        updatedResource.setUrl("https://example.com/updated.mp4");
        updatedResource.setCourse(course);

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(existingResource));
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(resourceRepository.save(any(Resource.class))).thenReturn(updatedResource);

        // Act
        ResourceResponseDTO result = resourceService.updateResource(resourceId, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(resourceId, result.getId());
        assertEquals(updateDTO.getType(), result.getType());
        assertEquals(updateDTO.getUrl(), result.getUrl());
        assertEquals(courseId, result.getCourseId());
        assertNull(result.getExerciseId());
        verify(resourceRepository).findById(resourceId);
        verify(courseRepository).findById(courseId);
        verify(resourceRepository).save(any(Resource.class));
    }

    /**
     * Vérifie que la mise à jour d'une ressource fonctionne correctement avec un exercice
     */
    @Test
    void updateResource_WithExerciseId_ShouldReturnUpdatedResourceResponseDTO() {
        // Arrange
        ResourceRequestDTO updateDTO = new ResourceRequestDTO();
        updateDTO.setType("VIDEO");
        updateDTO.setUrl("https://example.com/updated.mp4");
        updateDTO.setExerciseId(exerciseId);

        Resource existingResource = new Resource();
        existingResource.setId(resourceId);
        existingResource.setType("PDF");
        existingResource.setUrl("https://example.com/resource.pdf");

        Resource updatedResource = new Resource();
        updatedResource.setId(resourceId);
        updatedResource.setType("VIDEO");
        updatedResource.setUrl("https://example.com/updated.mp4");
        updatedResource.setExercise(exercise);

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(existingResource));
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exercise));
        when(resourceRepository.save(any(Resource.class))).thenReturn(updatedResource);

        // Act
        ResourceResponseDTO result = resourceService.updateResource(resourceId, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(resourceId, result.getId());
        assertEquals(updateDTO.getType(), result.getType());
        assertEquals(updateDTO.getUrl(), result.getUrl());
        assertEquals(exerciseId, result.getExerciseId());
        assertNull(result.getCourseId());
        verify(resourceRepository).findById(resourceId);
        verify(exerciseRepository).findById(exerciseId);
        verify(resourceRepository).save(any(Resource.class));
    }

    /**
     * Vérifie qu'une exception est levée quand la ressource n'existe pas lors de la mise à jour
     */
    @Test
    void updateResource_WithInvalidResourceId_ShouldThrowResourceNotFoundException() {
        // Arrange
        when(resourceRepository.findById(resourceId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            resourceService.updateResource(resourceId, resourceRequestDTO);
        });
        verify(resourceRepository).findById(resourceId);
        verify(resourceRepository, never()).save(any(Resource.class));
    }

    /**
     * Vérifie qu'une exception est levée quand on ne spécifie ni courseId ni exerciseId lors de la mise à jour
     */
    @Test
    void updateResource_WithNoParent_ShouldThrowIllegalArgumentException() {
        // Arrange
        Resource existingResource = new Resource();
        existingResource.setId(resourceId);
        existingResource.setType("PDF");
        existingResource.setUrl("https://example.com/resource.pdf");

        ResourceRequestDTO updateDTO = new ResourceRequestDTO();
        updateDTO.setType("VIDEO");
        updateDTO.setUrl("https://example.com/updated.mp4");
        updateDTO.setCourseId(null);
        updateDTO.setExerciseId(null);

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(existingResource));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            resourceService.updateResource(resourceId, updateDTO);
        });

        assertEquals("Une ressource doit être liée à un Course ou un Exercise.", exception.getMessage());
        verify(resourceRepository).findById(resourceId);
        verify(resourceRepository, never()).save(any(Resource.class));
    }

    /**
     * Vérifie qu'une exception est levée quand on spécifie les deux parents lors de la mise à jour
     */
    @Test
    void updateResource_WithBothParents_ShouldThrowIllegalArgumentException() {
        // Arrange
        Resource existingResource = new Resource();
        existingResource.setId(resourceId);
        existingResource.setType("PDF");
        existingResource.setUrl("https://example.com/resource.pdf");

        ResourceRequestDTO updateDTO = new ResourceRequestDTO();
        updateDTO.setType("VIDEO");
        updateDTO.setUrl("https://example.com/updated.mp4");
        updateDTO.setCourseId(courseId);
        updateDTO.setExerciseId(exerciseId);

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(existingResource));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            resourceService.updateResource(resourceId, updateDTO);
        });

        assertEquals("Une ressource ne peut être liée qu'à un seul parent (Course OU Exercise).", exception.getMessage());
        verify(resourceRepository).findById(resourceId);
        verify(resourceRepository, never()).save(any(Resource.class));
    }

    /**
     * Vérifie la suppression d'une ressource fonctionne correctement
     */
    @Test
    void deleteResource_WithValidId_ShouldDeleteResource() {
        // Arrange
        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));
        doNothing().when(resourceRepository).delete(any(Resource.class));

        // Act
        resourceService.deleteResource(resourceId);

        // Assert
        verify(resourceRepository).findById(resourceId);
        verify(resourceRepository).delete(resource);
    }

    /**
     * Vérifie qu'une exception est levée quand la ressource n'existe pas lors de la suppression
     */
    @Test
    void deleteResource_WithInvalidId_ShouldThrowResourceNotFoundException() {
        // Arrange
        when(resourceRepository.findById(resourceId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            resourceService.deleteResource(resourceId);
        });
        verify(resourceRepository).findById(resourceId);
        verify(resourceRepository, never()).delete(any(Resource.class));
    }
}