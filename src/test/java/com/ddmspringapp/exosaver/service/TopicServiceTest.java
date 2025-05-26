package com.ddmspringapp.exosaver.service;

import com.ddmspringapp.exosaver.Exceptions.CourseException.CourseNotFoundException;
import com.ddmspringapp.exosaver.Exceptions.TopicException.TopicNotFoundException;
import com.ddmspringapp.exosaver.Exceptions.TopicException.TopicNotInCourseException;
import com.ddmspringapp.exosaver.dto.TopicDTO.TopicRequestDTO;
import com.ddmspringapp.exosaver.dto.TopicDTO.TopicResponseDTO;
import com.ddmspringapp.exosaver.mapper.TopicMapper;
import com.ddmspringapp.exosaver.model.Course;
import com.ddmspringapp.exosaver.model.Topic;
import com.ddmspringapp.exosaver.repository.CourseRepository;
import com.ddmspringapp.exosaver.repository.TopicRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private TopicService topicService;

    private Course course;
    private Topic topic;
    private TopicRequestDTO topicRequestDTO;
    private TopicResponseDTO topicResponseDTO;
    private Long courseId = 1L;
    private Long topicId = 1L;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(courseId);
        course.setTitle("Test Course");

        topic = new Topic();
        topic.setId(topicId);
        topic.setTitle("Test Topic");
        topic.setDescription("Test Description");
        topic.setCourse(course);

        topicRequestDTO = new TopicRequestDTO();
        topicRequestDTO.setTitle("Test Topic");
        topicRequestDTO.setDescription("Test Description");

        topicResponseDTO = new TopicResponseDTO();
        topicResponseDTO.setId(topicId);
        topicResponseDTO.setTitle("Test Topic");
        topicResponseDTO.setDescription("Test Description");
    }

    /**
     * Vérifie que la création d'un topic fonctionne correctement avec des données valides
     */
    @Test
    void createTopic_WithValidData_ShouldReturnTopicResponseDTO() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        // Act
        TopicResponseDTO result = topicService.createTopic(courseId, topicRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(topicId, result.getId());
        assertEquals(topicRequestDTO.getTitle(), result.getTitle());
        assertEquals(topicRequestDTO.getDescription(), result.getDescription());
        verify(courseRepository).findById(courseId);
        verify(topicRepository).save(any(Topic.class));
    }

    /**
     * Vérifie qu'une exception est levée quand le cours n'existe pas
     */
    @Test
    void createTopic_WithInvalidCourseId_ShouldThrowCourseNotFoundException() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CourseNotFoundException.class, () -> {
            topicService.createTopic(courseId, topicRequestDTO);
        });
        verify(courseRepository).findById(courseId);
        verify(topicRepository, never()).save(any(Topic.class));
    }

    /**
     * Vérifie que la récupération d'un topic par ID fonctionne correctement
     */
    @Test
    void getTopicById_WithValidIds_ShouldReturnTopicResponseDTO() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(topicRepository.findByIdAndCourseId(topicId, courseId)).thenReturn(Optional.of(topic));

        // Act
        TopicResponseDTO result = topicService.getTopicById(courseId, topicId);

        // Assert
        assertNotNull(result);
        assertEquals(topicId, result.getId());
        assertEquals(topic.getTitle(), result.getTitle());
        assertEquals(topic.getDescription(), result.getDescription());
        verify(courseRepository).findById(courseId);
        verify(topicRepository).findByIdAndCourseId(topicId, courseId);
    }

    /**
     * Vérifie qu'une exception est levée quand le cours n'existe pas lors de la récupération d'un topic
     */
    @Test
    void getTopicById_WithInvalidCourseId_ShouldThrowCourseNotFoundException() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CourseNotFoundException.class, () -> {
            topicService.getTopicById(courseId, topicId);
        });
        verify(courseRepository).findById(courseId);
        verify(topicRepository, never()).findByIdAndCourseId(anyLong(), anyLong());
    }

    /**
     * Vérifie qu'une exception est levée quand le topic n'appartient pas au cours
     */
    @Test
    void getTopicById_WithTopicNotInCourse_ShouldThrowTopicNotInCourseException() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(topicRepository.findByIdAndCourseId(topicId, courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TopicNotInCourseException.class, () -> {
            topicService.getTopicById(courseId, topicId);
        });
        verify(courseRepository).findById(courseId);
        verify(topicRepository).findByIdAndCourseId(topicId, courseId);
    }

    /**
     * Vérifie que la récupération de tous les topics d'un cours fonctionne correctement
     */
    @Test
    void getAllTopics_WithValidCourseId_ShouldReturnListOfTopicResponseDTO() {
        // Arrange
        Topic topic2 = new Topic();
        topic2.setId(2L);
        topic2.setTitle("Test Topic 2");
        topic2.setDescription("Test Description 2");
        topic2.setCourse(course);

        List<Topic> topics = Arrays.asList(topic, topic2);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(topicRepository.findByCourseId(courseId)).thenReturn(topics);

        // Act
        List<TopicResponseDTO> result = topicService.getAllTopics(courseId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(topicId, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(courseRepository).findById(courseId);
        verify(topicRepository).findByCourseId(courseId);
    }

    /**
     * Vérifie qu'une exception est levée quand le cours n'existe pas lors de la récupération de tous les topics
     */
    @Test
    void getAllTopics_WithInvalidCourseId_ShouldThrowCourseNotFoundException() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CourseNotFoundException.class, () -> {
            topicService.getAllTopics(courseId);
        });
        verify(courseRepository).findById(courseId);
        verify(topicRepository, never()).findByCourseId(anyLong());
    }

    /**
     * Vérifie que la mise à jour d'un topic fonctionne correctement
     */
    @Test
    void updateTopic_WithValidData_ShouldReturnUpdatedTopicResponseDTO() {
        // Arrange
        TopicRequestDTO updateDTO = new TopicRequestDTO();
        updateDTO.setTitle("Updated Title");
        updateDTO.setDescription("Updated Description");

        Topic updatedTopic = new Topic();
        updatedTopic.setId(topicId);
        updatedTopic.setTitle("Updated Title");
        updatedTopic.setDescription("Updated Description");
        updatedTopic.setCourse(course);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(topicRepository.findByIdAndCourseId(topicId, courseId)).thenReturn(Optional.of(topic));
        when(topicRepository.save(any(Topic.class))).thenReturn(updatedTopic);

        // Act
        TopicResponseDTO result = topicService.updateTopic(topicId, courseId, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(topicId, result.getId());
        assertEquals(updateDTO.getTitle(), result.getTitle());
        assertEquals(updateDTO.getDescription(), result.getDescription());
        verify(courseRepository).findById(courseId);
        verify(topicRepository).findByIdAndCourseId(topicId, courseId);
        verify(topicRepository).save(any(Topic.class));
    }

    /**
     * Vérifie qu'une exception est levée quand le cours n'existe pas lors de la mise à jour d'un topic
     */
    @Test
    void updateTopic_WithInvalidCourseId_ShouldThrowCourseNotFoundException() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CourseNotFoundException.class, () -> {
            topicService.updateTopic(topicId, courseId, topicRequestDTO);
        });
        verify(courseRepository).findById(courseId);
        verify(topicRepository, never()).findByIdAndCourseId(anyLong(), anyLong());
        verify(topicRepository, never()).save(any(Topic.class));
    }

    /**
     * Vérifie qu'une exception est levée quand le topic n'existe pas lors de la mise à jour
     */
    @Test
    void updateTopic_WithInvalidTopicId_ShouldThrowTopicNotFoundException() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(topicRepository.findByIdAndCourseId(topicId, courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TopicNotFoundException.class, () -> {
            topicService.updateTopic(topicId, courseId, topicRequestDTO);
        });
        verify(courseRepository).findById(courseId);
        verify(topicRepository).findByIdAndCourseId(topicId, courseId);
        verify(topicRepository, never()).save(any(Topic.class));
    }

    /**
     * Vérifie que la suppression d'un topic fonctionne correctement
     */
    @Test
    void deleteTopic_WithValidIds_ShouldDeleteTopic() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(topicRepository.findByIdAndCourseId(topicId, courseId)).thenReturn(Optional.of(topic));
        doNothing().when(topicRepository).delete(any(Topic.class));

        // Act
        topicService.deleteTopic(courseId, topicId);

        // Assert
        verify(courseRepository).findById(courseId);
        verify(topicRepository).findByIdAndCourseId(topicId, courseId);
        verify(topicRepository).delete(topic);
    }

    /**
     * Vérifie qu'une exception est levée quand le cours n'existe pas lors de la suppression d'un topic
     */
    @Test
    void deleteTopic_WithInvalidCourseId_ShouldThrowCourseNotFoundException() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CourseNotFoundException.class, () -> {
            topicService.deleteTopic(courseId, topicId);
        });
        verify(courseRepository).findById(courseId);
        verify(topicRepository, never()).findByIdAndCourseId(anyLong(), anyLong());
        verify(topicRepository, never()).delete(any(Topic.class));
    }

    /**
     * Vérifie qu'une exception est levée quand le topic n'existe pas lors de la suppression
     */
    @Test
    void deleteTopic_WithInvalidTopicId_ShouldThrowTopicNotFoundException() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(topicRepository.findByIdAndCourseId(topicId, courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(TopicNotFoundException.class, () -> {
            topicService.deleteTopic(courseId, topicId);
        });
        verify(courseRepository).findById(courseId);
        verify(topicRepository).findByIdAndCourseId(topicId, courseId);
        verify(topicRepository, never()).delete(any(Topic.class));
    }
}