package com.ddmspringapp.exosaver.service;

import com.ddmspringapp.exosaver.Exceptions.ExerciceException.ExerciseNotFoundException;
import com.ddmspringapp.exosaver.Exceptions.TopicException.TopicNotFoundException;
import com.ddmspringapp.exosaver.Exceptions.TopicException.TopicNotInCourseException;
import com.ddmspringapp.exosaver.dto.ExerciseDTO.ExerciseRequestDTO;
import com.ddmspringapp.exosaver.dto.ExerciseDTO.ExerciseResponseDTO;
import com.ddmspringapp.exosaver.model.Exercise;
import com.ddmspringapp.exosaver.model.FeynmanStatus;
import com.ddmspringapp.exosaver.model.Topic;
import com.ddmspringapp.exosaver.model.Course;
import com.ddmspringapp.exosaver.repository.ExerciseRepository;
import com.ddmspringapp.exosaver.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    private Long exerciseId;
    private Long topicId;
    private Long courseId;
    private Exercise exercise;
    private Topic topic;
    private Course course;
    private ExerciseRequestDTO exerciseRequestDTO;
    private ExerciseResponseDTO exerciseResponseDTO;

    @BeforeEach
    public void setUp() {
        exerciseId = 1L;
        topicId = 2L;
        courseId = 3L;

        // Initialiser l'objet Course
        course = new Course();
        course.setId(courseId);
        course.setTitle("Test Course");

        // Initialiser l'objet Topic
        topic = new Topic();
        topic.setId(topicId);
        topic.setTitle("Test Topic");
        topic.setCourse(course);

        // Initialiser l'objet Exercise
        exercise = new Exercise();
        exercise.setId(exerciseId);
        exercise.setTitle("Test Exercise");
        exercise.setDescription("Test Description");
        exercise.setTopic(topic);
        exercise.setFeynmanStatus(FeynmanStatus.NOT_STARTED);
        exercise.setFeynmanSuccessCount(0);

        // Initialiser l'objet ExerciseRequestDTO
        exerciseRequestDTO = new ExerciseRequestDTO();
        exerciseRequestDTO.setTitle("Test Exercise");
        exerciseRequestDTO.setDescription("Test Description");

        // Initialiser l'objet ExerciseResponseDTO
        exerciseResponseDTO = new ExerciseResponseDTO();
        exerciseResponseDTO.setId(exerciseId);
        exerciseResponseDTO.setTitle("Test Exercise");
        exerciseResponseDTO.setDescription("Test Description");
    }

    /**
     * Teste la création d'un exercice avec des données valides
     * Vérifie que l'exercice est correctement créé et que le DTO de retour est valide
     */
    @Test
    public void testCreateExercise_WithValidData_ShouldReturnExerciseResponseDTO() {
        // Arrange
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);

        // Act
        ExerciseResponseDTO result = exerciseService.createExercise(exerciseRequestDTO, topicId, courseId);

        // Assert
        assertNotNull(result);
        verify(topicRepository, times(1)).findById(topicId);
        verify(exerciseRepository, times(1)).save(any(Exercise.class));
    }

    /**
     * Teste la création d'un exercice quand le topic n'existe pas
     * Vérifie qu'une exception TopicNotFoundException est bien levée
     */
    @Test
    public void testCreateExercise_WithNonExistentTopic_ShouldThrowTopicNotFoundException() {
        // Arrange
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TopicNotFoundException.class, () -> exerciseService.createExercise(exerciseRequestDTO, topicId, courseId));
        verify(topicRepository, times(1)).findById(topicId);
        verify(exerciseRepository, never()).save(any(Exercise.class));
    }

    /**
     * Teste la création d'un exercice quand le topic n'appartient pas au cours spécifié
     * Vérifie qu'une exception TopicNotInCourseException est bien levée
     */
    @Test
    public void testCreateExercise_WhenTopicNotInCourse_ShouldThrowTopicNotInCourseException() {
        // Arrange
        Long wrongCourseId = 999L;
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));

        // Act & Assert
        assertThrows(TopicNotInCourseException.class, () -> exerciseService.createExercise(exerciseRequestDTO, topicId, wrongCourseId));
        verify(topicRepository, times(1)).findById(topicId);
        verify(exerciseRepository, never()).save(any(Exercise.class));
    }

    /**
     * Teste la récupération d'un exercice par son ID avec des données valides
     * Vérifie que l'exercice est correctement récupéré et que le DTO de retour est valide
     */
    @Test
    public void testGetExerciseById_WithValidData_ShouldReturnExerciseResponseDTO() {
        // Arrange
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(exerciseRepository.findByIdAndTopicId(exerciseId, topicId)).thenReturn(Optional.of(exercise));

        // Act
        ExerciseResponseDTO result = exerciseService.getExerciseById(exerciseId, topicId, courseId);

        // Assert
        assertNotNull(result);
        verify(topicRepository, times(1)).findById(topicId);
        verify(exerciseRepository, times(1)).findByIdAndTopicId(exerciseId, topicId);
    }

    /**
     * Teste la récupération d'un exercice quand le topic n'existe pas
     * Vérifie qu'une exception TopicNotFoundException est bien levée
     */
    @Test
    public void testGetExerciseById_WithNonExistentTopic_ShouldThrowTopicNotFoundException() {
        // Arrange
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TopicNotFoundException.class, () -> exerciseService.getExerciseById(exerciseId, topicId, courseId));
        verify(topicRepository, times(1)).findById(topicId);
        verify(exerciseRepository, never()).findByIdAndTopicId(anyLong(), anyLong());
    }

    /**
     * Teste la récupération d'un exercice quand l'exercice n'existe pas
     * Vérifie qu'une exception ExerciseNotFoundException est bien levée
     */
    @Test
    public void testGetExerciseById_WithNonExistentExercise_ShouldThrowExerciseNotFoundException() {
        // Arrange
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(exerciseRepository.findByIdAndTopicId(exerciseId, topicId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ExerciseNotFoundException.class, () -> exerciseService.getExerciseById(exerciseId, topicId, courseId));
        verify(topicRepository, times(1)).findById(topicId);
        verify(exerciseRepository, times(1)).findByIdAndTopicId(exerciseId, topicId);
    }

    /**
     * Teste la récupération de tous les exercices pour un topic et un cours valides
     * Vérifie que tous les exercices sont correctement récupérés et convertis en DTOs
     */
    @Test
    public void testGetAllExercises_WithValidData_ShouldReturnListOfExerciseResponseDTO() {
        // Arrange
        Exercise exercise2 = new Exercise();
        exercise2.setId(2L);
        exercise2.setTitle("Second Exercise");
        exercise2.setTopic(topic);

        List<Exercise> exercises = Arrays.asList(exercise, exercise2);
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(exerciseRepository.findByTopicId(topicId)).thenReturn(exercises);

        // Act
        List<ExerciseResponseDTO> results = exerciseService.getAllExercises(topicId, courseId);

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        verify(topicRepository, times(1)).findById(topicId);
        verify(exerciseRepository, times(1)).findByTopicId(topicId);
    }

    /**
     * Teste la récupération de tous les exercices quand aucun exercice n'existe
     * Vérifie qu'une liste vide est retournée
     */
    @Test
    public void testGetAllExercises_WhenNoExercisesExist_ShouldReturnEmptyList() {
        // Arrange
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(exerciseRepository.findByTopicId(topicId)).thenReturn(List.of());

        // Act
        List<ExerciseResponseDTO> results = exerciseService.getAllExercises(topicId, courseId);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(topicRepository, times(1)).findById(topicId);
        verify(exerciseRepository, times(1)).findByTopicId(topicId);
    }

    /**
     * Teste la mise à jour d'un exercice existant avec des données valides
     * Vérifie que les données de l'exercice sont correctement mises à jour
     */
    @Test
    public void testUpdateExercise_WithValidData_ShouldReturnUpdatedExerciseResponseDTO() {
        // Arrange
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(exerciseRepository.findByIdAndTopicId(exerciseId, topicId)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.save(exercise)).thenReturn(exercise);

        ExerciseRequestDTO updateDTO = new ExerciseRequestDTO();
        updateDTO.setTitle("Updated Title");
        updateDTO.setDescription("Updated Description");

        // Act
        ExerciseResponseDTO result = exerciseService.updateExercise(exerciseId, updateDTO, topicId, courseId);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Title", exercise.getTitle());
        assertEquals("Updated Description", exercise.getDescription());
        verify(topicRepository, times(1)).findById(topicId);
        verify(exerciseRepository, times(1)).findByIdAndTopicId(exerciseId, topicId);
        verify(exerciseRepository, times(1)).save(exercise);
    }

    /**
     * Teste la suppression d'un exercice existant
     * Vérifie que la méthode delete du repository est bien appelée
     */
    @Test
    public void testDeleteExercise_WhenExerciseExists_ShouldDeleteExercise() {
        // Arrange
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(exerciseRepository.findByIdAndTopicId(exerciseId, topicId)).thenReturn(Optional.of(exercise));
        doNothing().when(exerciseRepository).delete(exercise);

        // Act
        exerciseService.deleteExercise(exerciseId, topicId, courseId);

        // Assert
        verify(topicRepository, times(1)).findById(topicId);
        verify(exerciseRepository, times(1)).findByIdAndTopicId(exerciseId, topicId);
        verify(exerciseRepository, times(1)).delete(exercise);
    }

    /**
     * Teste la mise à jour du statut Feynman à EXPLAINED_OK
     * Vérifie que le compteur de succès est incrémenté et que la prochaine date de revue est correctement calculée
     */
    @Test
    public void testUpdateFeynmanStatus_WhenStatusIsExplainedOk_ShouldUpdateSuccessCountAndNextReviewDate() {
        // Arrange
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(exerciseRepository.findByIdAndTopicId(exerciseId, topicId)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.save(exercise)).thenReturn(exercise);

        // Act
        exerciseService.UpdateFeynmanStatus(exerciseId, topicId, courseId, FeynmanStatus.EXPLAINED_OK);

        // Assert
        assertEquals(FeynmanStatus.EXPLAINED_OK, exercise.getFeynmanStatus());
        assertEquals(1, exercise.getFeynmanSuccessCount());
        assertNotNull(exercise.getNextReviewDate());
        // Vérifier que la date de revue est dans 3 jours (premier succès)
        assertTrue(exercise.getNextReviewDate().isAfter(LocalDateTime.now().plusDays(2)));
        assertTrue(exercise.getNextReviewDate().isBefore(LocalDateTime.now().plusDays(4)));

        verify(exerciseRepository, times(1)).save(exercise);
    }

    /**
     * Teste la mise à jour du statut Feynman à NEEDS_WORK
     * Vérifie que le compteur de succès est remis à zéro et que la prochaine date de revue est fixée à demain
     */
    @Test
    public void testUpdateFeynmanStatus_WhenStatusIsNeedsWork_ShouldResetSuccessCountAndSetNextReviewDateTomorrow() {
        // Arrange
        exercise.setFeynmanSuccessCount(2); // Supposons qu'il y avait déjà 2 succès

        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(exerciseRepository.findByIdAndTopicId(exerciseId, topicId)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.save(exercise)).thenReturn(exercise);

        // Act
        exerciseService.UpdateFeynmanStatus(exerciseId, topicId, courseId, FeynmanStatus.NEEDS_WORK);

        // Assert
        assertEquals(FeynmanStatus.NEEDS_WORK, exercise.getFeynmanStatus());
        assertEquals(0, exercise.getFeynmanSuccessCount());
        assertNotNull(exercise.getNextReviewDate());
        // Vérifier que la date de revue est demain
        assertTrue(exercise.getNextReviewDate().isAfter(LocalDateTime.now()));
        assertTrue(exercise.getNextReviewDate().isBefore(LocalDateTime.now().plusDays(2)));

        verify(exerciseRepository, times(1)).save(exercise);
    }

    /**
     * Teste la mise à jour du statut Feynman avec différents compteurs de succès
     * Vérifie que la prochaine date de revue est correctement calculée selon le nombre de succès
     */
    @Test
    public void testUpdateFeynmanStatus_WithDifferentSuccessCounts_ShouldSetCorrectNextReviewDate() {
        // Arrange
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));
        when(exerciseRepository.findByIdAndTopicId(exerciseId, topicId)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.save(exercise)).thenReturn(exercise);

        // Premier succès - 3 jours
        exerciseService.UpdateFeynmanStatus(exerciseId, topicId, courseId, FeynmanStatus.REVIEW_OK);
        assertTrue(exercise.getNextReviewDate().isAfter(LocalDateTime.now().plusDays(2)));
        assertTrue(exercise.getNextReviewDate().isBefore(LocalDateTime.now().plusDays(4)));
        assertEquals(1, exercise.getFeynmanSuccessCount());

        // Deuxième succès - 6 jours
        exerciseService.UpdateFeynmanStatus(exerciseId, topicId, courseId, FeynmanStatus.EXPLAINED_OK);
        assertTrue(exercise.getNextReviewDate().isAfter(LocalDateTime.now().plusDays(5)));
        assertTrue(exercise.getNextReviewDate().isBefore(LocalDateTime.now().plusDays(7)));
        assertEquals(2, exercise.getFeynmanSuccessCount());

        // Troisième succès - 10 jours
        exerciseService.UpdateFeynmanStatus(exerciseId, topicId, courseId, FeynmanStatus.REVIEW_OK);
        assertTrue(exercise.getNextReviewDate().isAfter(LocalDateTime.now().plusDays(9)));
        assertTrue(exercise.getNextReviewDate().isBefore(LocalDateTime.now().plusDays(11)));
        assertEquals(3, exercise.getFeynmanSuccessCount());

        // Quatrième succès et plus - 15 jours
        exerciseService.UpdateFeynmanStatus(exerciseId, topicId, courseId, FeynmanStatus.EXPLAINED_OK);
        assertTrue(exercise.getNextReviewDate().isAfter(LocalDateTime.now().plusDays(14)));
        assertTrue(exercise.getNextReviewDate().isBefore(LocalDateTime.now().plusDays(16)));
        assertEquals(4, exercise.getFeynmanSuccessCount());

        verify(exerciseRepository, times(4)).save(exercise);
    }
}