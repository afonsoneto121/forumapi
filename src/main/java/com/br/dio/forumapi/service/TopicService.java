package com.br.dio.forumapi.service;

import com.br.dio.forumapi.dto.request.AnswerDTO;
import com.br.dio.forumapi.dto.request.TopicDTO;
import com.br.dio.forumapi.dto.response.MessageResponseDTO;
import com.br.dio.forumapi.entity.Answer;
import com.br.dio.forumapi.entity.Topic;
import com.br.dio.forumapi.enums.ParamsType;
import com.br.dio.forumapi.enums.SubjectType;
import com.br.dio.forumapi.exception.TopicNotFoundException;
import com.br.dio.forumapi.mapper.AnswerMapper;
import com.br.dio.forumapi.mapper.TopicMapper;
import com.br.dio.forumapi.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Topic> findByParams(Map<String, String> allParams) {
        if(allParams.isEmpty()) {
            return this.findAll();
        }
        Map<String, String> params = removeAllInvalidParams(allParams);

        if(params.isEmpty()) {
            return this.findAll();
        }

        int page = Integer.parseInt(params.getOrDefault(ParamsType.PAGE.getDescription(),ParamsType.PAGE.getDefaultValue()));
        int limit = Integer.parseInt(params.getOrDefault(ParamsType.LIMIT.getDescription(),ParamsType.LIMIT.getDefaultValue()));

        Pageable pageRequest;

        if(params.containsKey(ParamsType.SORT.getDescription())) {
            String order = params.getOrDefault(ParamsType.LIMIT.getDescription(),ParamsType.LIMIT.getDefaultValue());
            pageRequest = PageRequest.of(
                    page,
                    limit,
                    order.equals("desc") ? Sort.Direction.DESC: Sort.Direction.ASC,
                    params.get(ParamsType.SORT.getDescription())
                    );
        } else {
            pageRequest = PageRequest.of(page,limit);
        }
        if(params.containsKey(ParamsType.Q.getDescription())) {
            String valueQ = params.get(ParamsType.Q.getDescription());
            return topicRepository.findByValueQ(valueQ,pageRequest);
        }
        if(params.containsKey(ParamsType.TITLE.getDescription()) && params.containsKey(ParamsType.DESCRIPTION.getDescription())) {
            String title = params.get(ParamsType.TITLE.getDescription());
            String description = params.get(ParamsType.DESCRIPTION.getDescription());
            return topicRepository.findByTitleAndDescription(title,description,pageRequest);
        }
        if(params.containsKey(ParamsType.DESCRIPTION.getDescription())) {
            String description = params.get(ParamsType.DESCRIPTION.getDescription());
            return topicRepository.findByDescription(description,pageRequest);
        }
        if(params.containsKey(ParamsType.TITLE.getDescription()) ) {
            String title = params.get(ParamsType.TITLE.getDescription());
            return topicRepository.findByTitle(title,pageRequest);
        }
        return topicRepository.findAll(pageRequest).getContent();
    }

    private Map<String, String> removeAllInvalidParams(Map<String, String> allParams) {
        return allParams.entrySet()
                .stream()
                .filter(map -> {
                    return Arrays.stream(ParamsType.values())
                            .anyMatch((parms) -> parms.getDescription().equals(map.getKey().toLowerCase()));
                }).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }
}
