package com.br.dio.forumapi.controller;

import com.br.dio.forumapi.dto.request.TopicDTO;
import com.br.dio.forumapi.dto.response.MessageResponseDTO;
import com.br.dio.forumapi.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/topic")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TopicController {
    private TopicService topicService;

    @PostMapping
    @ResponseStatus (HttpStatus.CREATED)
    private MessageResponseDTO createTopic(@RequestBody @Valid TopicDTO topic) {
        return topicService.createTopic(topic);
    }
}
