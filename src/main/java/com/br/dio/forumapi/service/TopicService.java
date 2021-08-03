package com.br.dio.forumapi.service;

import com.br.dio.forumapi.dto.request.AnswerDTO;
import com.br.dio.forumapi.dto.request.TopicDTO;
import com.br.dio.forumapi.dto.response.MessageResponseDTO;
import com.br.dio.forumapi.entity.Answer;
import com.br.dio.forumapi.entity.Topic;
import com.br.dio.forumapi.enums.SubjectType;
import com.br.dio.forumapi.exception.TopicNotFoundException;
import com.br.dio.forumapi.mapper.AnswerMapper;
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
    private final AnswerMapper answerMapper = AnswerMapper.INSTANCE;

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

    public Topic findById(Long id) throws TopicNotFoundException {
        return verifyIfExists(id);
    }

    public List<Topic> findByResolved() {
        return topicRepository.findByResolved();
    }

    public List<Topic> findByUnResolved() {
        return topicRepository.findByUnResolved();
    }

    public MessageResponseDTO createAnswer(Long id, AnswerDTO answerDTO) throws TopicNotFoundException {
        Topic topic = verifyIfExists(id);
        List<Answer> answers = topic.getAnswers();
        answers.add(answerMapper.toModel(answerDTO));
        topic.setAnswers(answers);
        Topic updatedTopic = topicRepository.save(topic);
        return MessageResponseDTO.builder()
                .message("Answer was created with ID " + updatedTopic.getId())
                .build();
    }

    private Topic verifyIfExists(Long id) throws TopicNotFoundException {
        return topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));
    }
}
