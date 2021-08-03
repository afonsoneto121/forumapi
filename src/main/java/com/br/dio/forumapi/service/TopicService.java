package com.br.dio.forumapi.service;

import com.br.dio.forumapi.dto.request.TopicDTO;
import com.br.dio.forumapi.dto.response.MessageResponseDTO;
import com.br.dio.forumapi.entity.Topic;
import com.br.dio.forumapi.enums.SubjectType;
import com.br.dio.forumapi.mapper.TopicMapper;
import com.br.dio.forumapi.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TopicService {

    private TopicRepository topicRepository;
    private final TopicMapper topicMapper = TopicMapper.INSTANCE;
    public MessageResponseDTO createTopic(TopicDTO topicDTO) {
        Topic topic = topicRepository.save(topicMapper.toModel(topicDTO));
        return MessageResponseDTO.builder()
                .message("Topic was created with ID " + topic.getId())
                .build();
    }

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    public List<Topic> findBySubject(SubjectType subjectType) {
        return topicRepository.findBySubject(subjectType);
    }
}
