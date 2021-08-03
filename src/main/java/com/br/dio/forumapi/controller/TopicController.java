package com.br.dio.forumapi.controller;

import com.br.dio.forumapi.dto.request.TopicDTO;
import com.br.dio.forumapi.dto.response.MessageResponseDTO;
import com.br.dio.forumapi.entity.Topic;
import com.br.dio.forumapi.enums.SubjectType;
import com.br.dio.forumapi.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/topic")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TopicController {
    private TopicService topicService;

    @PostMapping
    @ResponseStatus (HttpStatus.CREATED)
    public MessageResponseDTO createTopic(@RequestBody @Valid TopicDTO topic) {
        return topicService.createTopic(topic);
    }
    @GetMapping
    public List<Topic> findAll() {
        return topicService.findAll();
    }
    @GetMapping("/{subjectType}")
    public List<Topic> findBySubject(@PathVariable @Valid SubjectType subjectType) {
        return topicService.findBySubject(subjectType);
    }
}
