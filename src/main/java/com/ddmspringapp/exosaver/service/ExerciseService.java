package com.ddmspringapp.exosaver.service;

import com.ddmspringapp.exosaver.Exceptions.ExerciceException.ExerciseNotFoundException;
import com.ddmspringapp.exosaver.Exceptions.TopicException.TopicNotFoundException;
import com.ddmspringapp.exosaver.Exceptions.TopicException.TopicNotInCourseException;
import com.ddmspringapp.exosaver.dto.ExerciseDTO.ExerciseRequestDTO;
import com.ddmspringapp.exosaver.dto.ExerciseDTO.ExerciseResponseDTO;
import com.ddmspringapp.exosaver.mapper.ExerciseMapper;
import com.ddmspringapp.exosaver.model.Exercise;
import com.ddmspringapp.exosaver.model.FeynmanStatus;
import com.ddmspringapp.exosaver.model.Topic;
import com.ddmspringapp.exosaver.repository.ExerciseRepository;
import com.ddmspringapp.exosaver.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private TopicRepository topicRepository;

    public ExerciseResponseDTO createExercise(ExerciseRequestDTO dto, Long topicId, Long courseId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        if(!topic.getCourse().getId().equals(courseId)){
            throw new TopicNotInCourseException(topicId, courseId);
        }
        Exercise exercise = ExerciseMapper.toEntity(dto);
        exercise.setTopic(topic);
        exercise = exerciseRepository.save(exercise);
        return ExerciseMapper.toResponseDTO(exercise);
    }

    public ExerciseResponseDTO getExerciseById(Long exerciseId, Long topicId, Long courseId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        if(!topic.getCourse().getId().equals(courseId)){
            throw new TopicNotInCourseException(topicId, courseId);
        }
        Exercise exercise = exerciseRepository.findByIdAndTopicId(exerciseId, topicId)
                .orElseThrow(()-> new ExerciseNotFoundException(exerciseId));
        return ExerciseMapper.toResponseDTO(exercise);
    }

    public List<ExerciseResponseDTO> getAllExercises(Long topicId, Long courseId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        if(!topic.getCourse().getId().equals(courseId)){
            throw new TopicNotInCourseException(topicId, courseId);
        }
        List<Exercise> exercise = exerciseRepository.findByTopicId(topicId);
        return exercise.stream().map(ExerciseMapper::toResponseDTO).toList();
    }

    public ExerciseResponseDTO updateExercise(Long exerciseId, ExerciseRequestDTO dto, Long topicId, Long courseId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        if(!topic.getCourse().getId().equals(courseId)){
            throw new TopicNotInCourseException(topicId, courseId);
        }
        Exercise exercise = exerciseRepository.findByIdAndTopicId(exerciseId, topicId)
                .orElseThrow(()-> new ExerciseNotFoundException(exerciseId));
        exercise.setTitle(dto.getTitle());
        exercise.setDescription(dto.getDescription());
        exercise = exerciseRepository.save(exercise);
        return ExerciseMapper.toResponseDTO(exercise);
    }

    public void deleteExercise(Long exerciseId, Long topicId, Long courseId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        if(!topic.getCourse().getId().equals(courseId)){
            throw new TopicNotInCourseException(topicId, courseId);
        }
        Exercise exercise = exerciseRepository.findByIdAndTopicId(exerciseId, topicId)
                .orElseThrow(()-> new ExerciseNotFoundException(exerciseId));
        exerciseRepository.delete(exercise);
    }

    public void UpdateFeynmanStatus(Long exerciseId, Long topicId, Long courseId, FeynmanStatus newStatus){
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        if(!topic.getCourse().getId().equals(courseId)){
            throw new TopicNotInCourseException(topicId, courseId);
        }
        Exercise exercise = exerciseRepository.findByIdAndTopicId(exerciseId, topicId)
                .orElseThrow(()-> new ExerciseNotFoundException(exerciseId));

        exercise.setFeynmanStatus(newStatus);
        if (newStatus == FeynmanStatus.EXPLAINED_OK || newStatus == FeynmanStatus.REVIEW_OK){
            int count = exercise.getFeynmanSuccessCount() + 1;
            exercise.setFeynmanSuccessCount(count);

            int days = switch (count){
                case 1 -> 3;
                case 2 -> 6;
                case 3 -> 10;
                default -> 15;
            };
            exercise.setNextReviewDate(LocalDateTime.now().plusDays(days));
        } else {
            exercise.setFeynmanSuccessCount(0);
            exercise.setNextReviewDate(LocalDateTime.now().plusDays(1));
        }
        exerciseRepository.save(exercise);
    }
}

