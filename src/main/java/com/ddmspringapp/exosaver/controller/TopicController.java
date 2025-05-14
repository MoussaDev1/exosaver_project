package com.ddmspringapp.exosaver.controller;

import com.ddmspringapp.exosaver.dto.TopicDTO.TopicRequestDTO;
import com.ddmspringapp.exosaver.dto.TopicDTO.TopicResponseDTO;
import com.ddmspringapp.exosaver.model.Topic;
import com.ddmspringapp.exosaver.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course/{coursId}")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/topics")
    public ResponseEntity<TopicResponseDTO> createTopic(@RequestBody TopicRequestDTO dto, @PathVariable Long coursId){
        TopicResponseDTO created = topicService.createTopic(coursId ,dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/topics")
        public List<TopicResponseDTO> getAllTopics(@PathVariable Long coursId){
        return topicService.getAllTopics(coursId);
    }

    @GetMapping("/topic/{id}")
    public ResponseEntity<TopicResponseDTO> getTopicById(@PathVariable Long id, @PathVariable Long coursId){
        TopicResponseDTO topic = topicService.getTopicById(coursId ,id);
        return new ResponseEntity<>(topic, HttpStatus.OK);
    }

    @PutMapping("/topic/{id}")
    public ResponseEntity<TopicResponseDTO> updateTopic(@PathVariable Long id, @RequestBody TopicRequestDTO dto, @PathVariable Long coursId){
        TopicResponseDTO updated = topicService.updateTopic(coursId, id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/topic/{id}")
    public void deleteTopic(@PathVariable Long id, @PathVariable Long coursId){
        topicService.deleteTopic(coursId, id);
    }
}
