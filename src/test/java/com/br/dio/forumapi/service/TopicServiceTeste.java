package com.br.dio.forumapi.service;

import com.br.dio.forumapi.builder.TopicDTOBuilder;
import com.br.dio.forumapi.dto.request.TopicDTO;
import com.br.dio.forumapi.dto.response.MessageResponseDTO;
import com.br.dio.forumapi.entity.Topic;
import com.br.dio.forumapi.exception.TopicNotFoundException;
import com.br.dio.forumapi.mapper.TopicMapper;
import com.br.dio.forumapi.repository.TopicRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TopicServiceTeste {
    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicService topicService;

    private TopicMapper topicMapper = TopicMapper.INSTANCE;
    private Topic topic;
    private TopicDTO topicDTO;

    @BeforeEach
    void setup() {
        topicDTO = TopicDTOBuilder.builder().build().toTopicDTO();
        topic = topicMapper.toModel(topicDTO);
    }

    @Test
    void whenATopicThenCreateANewTopic() {
        when(topicRepository.save(topic)).thenReturn(topic);

        MessageResponseDTO msgSaved = topicService.createTopic(topicDTO);

        assertThat(msgSaved,
                Matchers.is(Matchers.equalTo(getBuild("Topic was created with ID " + topic.getId()))));
    }

    @Test
    void whenValidIdThenFindTopic() throws TopicNotFoundException {
        when(topicRepository.findById(topicDTO.getId())).thenReturn(Optional.of(topic));

        Topic topicFound = topicService.findById(topicDTO.getId());

        assertThat(topicFound, is(equalTo(topic)));
    }

    @Test
    void whenInvalidIdThenAExceptionShouldBeThrown() {
        when(topicRepository.findById(topicDTO.getId())).thenReturn(Optional.empty());

        assertThrows(TopicNotFoundException.class, ()-> topicService.findById(topicDTO.getId()));
    }

    private MessageResponseDTO getBuild(String message) {
        return MessageResponseDTO.builder()
                .message(message)
                .build();
    }
}
