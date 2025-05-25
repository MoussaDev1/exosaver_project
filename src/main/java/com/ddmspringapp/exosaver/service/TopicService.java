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
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;

    public TopicService(TopicRepository topicRepository, CourseRepository courseRepository) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
    }

    public TopicResponseDTO createTopic(Long coursId, TopicRequestDTO dto){
        Course course = courseRepository.findById(coursId)
                .orElseThrow(() -> new CourseNotFoundException(coursId));
        Topic topic = TopicMapper.toEntity(dto);
        topic.setCourse(course);
        topic = topicRepository.save(topic);
        return TopicMapper.toResponseDTO(topic);
    }

    public TopicResponseDTO getTopicById(Long courseId, Long topicId){
        courseRepository.findById(courseId)
                .orElseThrow(()-> new CourseNotFoundException(courseId));
        Topic topic = topicRepository.findByIdAndCourseId(topicId, courseId)
                .orElseThrow(() -> new TopicNotInCourseException(topicId, courseId));
        return TopicMapper.toResponseDTO(topic);
    }

    public List<TopicResponseDTO> getAllTopics(Long courseId){
        courseRepository.findById(courseId)
                .orElseThrow(()-> new CourseNotFoundException(courseId));
        List<Topic> topics = topicRepository.findByCourseId(courseId);
        return topics.stream().map(TopicMapper::toResponseDTO).toList();
    }

    public TopicResponseDTO updateTopic(Long topicId, Long courseId, @NotNull TopicRequestDTO dto){
        courseRepository.findById(courseId)
                .orElseThrow(()-> new CourseNotFoundException(courseId));
        Topic topic = topicRepository.findByIdAndCourseId(topicId, courseId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        topic.setTitle(dto.getTitle());
        topic.setDescription(dto.getDescription());
        Topic updatedTopic = topicRepository.save(topic);
        return TopicMapper.toResponseDTO(updatedTopic);
    }

    public void deleteTopic(Long courseId, Long id) {
        courseRepository.findById(courseId)
                .orElseThrow(()-> new CourseNotFoundException(courseId));
        Topic topic = topicRepository.findByIdAndCourseId(id, courseId)
                .orElseThrow(() -> new TopicNotFoundException(id));
        topicRepository.delete(topic);
    }
}
