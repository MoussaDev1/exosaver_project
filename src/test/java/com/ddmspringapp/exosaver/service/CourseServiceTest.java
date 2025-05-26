package com.ddmspringapp.exosaver.service;

import com.ddmspringapp.exosaver.Exceptions.CourseException.CourseNotFoundException;
import com.ddmspringapp.exosaver.dto.CourseDTO.CourseRequestDTO;
import com.ddmspringapp.exosaver.dto.CourseDTO.CourseResponseDTO;
import com.ddmspringapp.exosaver.mapper.CourseMapper;
import com.ddmspringapp.exosaver.model.Course;
import com.ddmspringapp.exosaver.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private CourseRequestDTO courseRequestDTO;
    private Course course;
    private CourseResponseDTO courseResponseDTO;
    private final Long courseId = 1L;

    @BeforeEach
    public void setup() {
        // Préparation des données de test
        courseRequestDTO = new CourseRequestDTO();
        courseRequestDTO.setTitle("Test Course");
        courseRequestDTO.setDescription("Test Description");
        courseRequestDTO.setObjectives("Test Objectives");
        courseRequestDTO.setThemes("Test Themes");

        course = new Course();
        course.setId(courseId);
        course.setTitle("Test Course");
        course.setDescription("Test Description");
        course.setObjectives("Test Objectives");
        course.setThemes("Test Themes");

        courseResponseDTO = new CourseResponseDTO();
        courseResponseDTO.setId(courseId);
        courseResponseDTO.setTitle("Test Course");
        courseResponseDTO.setDescription("Test Description");
        courseResponseDTO.setObjectives("Test Objectives");
        courseResponseDTO.setThemes("Test Themes");
    }

    /**
     * Test la création d'un cours avec des données valides
     * Vérifie que le cours est bien sauvegardé et que le DTO de réponse est correct
     */
    @Test
    public void testCreateCourse_WithValidData_ShouldReturnCourseResponseDTO() {
        // Arrange
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        CourseResponseDTO result = courseService.createCourse(courseRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(courseId, result.getId());
        assertEquals(courseRequestDTO.getTitle(), result.getTitle());
        assertEquals(courseRequestDTO.getDescription(), result.getDescription());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    /**
     * Test la récupération d'un cours par son ID quand le cours existe
     * Vérifie que le cours est correctement récupéré et converti en DTO
     */
    @Test
    public void testGetCoursesById_WhenCourseExists_ShouldReturnCourseResponseDTO() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act
        CourseResponseDTO result = courseService.getCoursesById(courseId);

        // Assert
        assertNotNull(result);
        assertEquals(courseId, result.getId());
        assertEquals(course.getTitle(), result.getTitle());
        verify(courseRepository, times(1)).findById(courseId);
    }

    /**
     * Test la récupération d'un cours par un ID inexistant
     * Vérifie qu'une exception CourseNotFoundException est bien levée
     */
    @Test
    public void testGetCoursesById_WhenCourseDoesNotExist_ShouldThrowCourseNotFoundException() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CourseNotFoundException.class, () -> courseService.getCoursesById(courseId));
        verify(courseRepository, times(1)).findById(courseId);
    }

    /**
     * Test la récupération de tous les cours
     * Vérifie que tous les cours sont correctement récupérés et convertis en DTOs
     */
    @Test
    public void testGetAllCourses_ShouldReturnListOfCourseResponseDTO() {
        // Arrange
        Course course2 = new Course();
        course2.setId(2L);
        course2.setTitle("Second Course");

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course, course2));

        // Act
        List<CourseResponseDTO> results = courseService.getAllCourses();

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(courseId, results.get(0).getId());
        assertEquals(2L, results.get(1).getId());
        verify(courseRepository, times(1)).findAll();
    }

    /**
     * Test la récupération de tous les cours quand aucun cours n'existe
     * Vérifie qu'une liste vide est retournée
     */
    @Test
    public void testGetAllCourses_WhenNoCoursesExist_ShouldReturnEmptyList() {
        // Arrange
        when(courseRepository.findAll()).thenReturn(List.of());

        // Act
        List<CourseResponseDTO> results = courseService.getAllCourses();

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());
        verify(courseRepository, times(1)).findAll();
    }

    /**
     * Test la mise à jour d'un cours existant avec des données valides
     * Vérifie que les données du cours sont correctement mises à jour
     */
    @Test
    public void testUpdateCourse_WhenCourseExists_ShouldUpdateAndReturnCourseResponseDTO() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseRequestDTO updateDTO = new CourseRequestDTO();
        updateDTO.setTitle("Updated Title");
        updateDTO.setDescription("Updated Description");
        updateDTO.setObjectives("Updated Objectives");
        updateDTO.setThemes("Updated Themes");

        // Act
        CourseResponseDTO result = courseService.updateCourse(courseId, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(updateDTO.getTitle(), course.getTitle());
        assertEquals(updateDTO.getDescription(), course.getDescription());
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(course);
    }

    /**
     * Test la mise à jour d'un cours inexistant
     * Vérifie qu'une exception CourseNotFoundException est bien levée
     */
    @Test
    public void testUpdateCourse_WhenCourseDoesNotExist_ShouldThrowCourseNotFoundException() {
        // Arrange
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CourseNotFoundException.class, () -> courseService.updateCourse(courseId, courseRequestDTO));
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, never()).save(any(Course.class));
    }

    /**
     * Test la suppression d'un cours existant
     * Vérifie que la méthode deleteById du repository est bien appelée
     */
    @Test
    public void testDeleteCourse_WhenCourseExists_ShouldDeleteCourse() {
        // Arrange
        when(courseRepository.existsById(courseId)).thenReturn(true);
        doNothing().when(courseRepository).deleteById(courseId);

        // Act
        courseService.deleteCourse(courseId);

        // Assert
        verify(courseRepository, times(1)).existsById(courseId);
        verify(courseRepository, times(1)).deleteById(courseId);
    }

    /**
     * Test la suppression d'un cours inexistant
     * Vérifie qu'une exception CourseNotFoundException est bien levée
     */
    @Test
    public void testDeleteCourse_WhenCourseDoesNotExist_ShouldThrowCourseNotFoundException() {
        // Arrange
        when(courseRepository.existsById(courseId)).thenReturn(false);

        // Act & Assert
        assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourse(courseId));
        verify(courseRepository, times(1)).existsById(courseId);
        verify(courseRepository, never()).deleteById(any());
    }
}