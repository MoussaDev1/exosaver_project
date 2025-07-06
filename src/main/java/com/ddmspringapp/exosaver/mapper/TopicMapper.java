package com.ddmspringapp.exosaver.mapper;

import com.ddmspringapp.exosaver.dto.TopicDTO.TopicRequestDTO;
import com.ddmspringapp.exosaver.dto.TopicDTO.TopicResponseDTO;
import com.ddmspringapp.exosaver.model.Topic;

public class TopicMapper {

    public static TopicResponseDTO toResponseDTO(Topic topic) {
        TopicResponseDTO dto = new TopicResponseDTO();
        dto.setId(topic.getId());
        dto.setTitle(topic.getTitle());
        dto.setDescription(topic.getDescription());
        if (topic.getCourse() != null) {
            dto.setCourseId(topic.getCourse().getId());
        }
        else {
            dto.setCourseId(null);
        }
        return dto;
    }

    public static Topic toEntity(TopicRequestDTO dto){
        Topic topic = new Topic();
        topic.setTitle(dto.getTitle());
        topic.setDescription(dto.getDescription());
        return topic;
    }
}
