package com.br.dio.forumapi.controller;

import com.br.dio.forumapi.builder.TopicDTOBuilder;
import com.br.dio.forumapi.dto.request.TopicDTO;
import com.br.dio.forumapi.dto.response.MessageResponseDTO;
import com.br.dio.forumapi.entity.Topic;
import com.br.dio.forumapi.enums.SubjectType;
import com.br.dio.forumapi.exception.TopicNotFoundException;
import com.br.dio.forumapi.mapper.TopicMapper;
import com.br.dio.forumapi.service.TopicService;
import com.br.dio.forumapi.utils.BuildMocks;
import com.br.dio.forumapi.utils.JsonConvertionUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.br.dio.forumapi.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TopicControllerTest {
    private String URL_API = "/api/v1/topic";

    @Mock
    private TopicService topicService;

    @InjectMocks
    private TopicController topicController;

    private MockMvc mockMvc;

    private Topic topic;
    private TopicDTO topicDTO;
    private TopicMapper topicMapper = TopicMapper.INSTANCE;
    private Map<String,String> param;
    @BeforeEach
    void setup() {
        topicDTO = TopicDTOBuilder.builder().build().toTopicDTO();
        topic = topicMapper.toModel(topicDTO);
        mockMvc = MockMvcBuilders.standaloneSetup(topicController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
        param = new HashMap<>();
    }

    @Test
    void whenPOSTCreateTopicThenReturnANewTopic() throws Exception {
        when(topicService.createTopic(topicDTO)).thenReturn(getBuild("Topic was created with ID " + topic.getId()));

        mockMvc.perform(MockMvcRequestBuilders.post(URL_API)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(topicDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message",
                        is(equalTo("Topic was created with ID " + topic.getId()))));
    }

    @Test
    void whenGETIsCalledWithValidIdThenReturnATopic() throws Exception {
        when(topicService.findById(topicDTO.getId())).thenReturn(topic);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_API+"/id/"+topicDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(topicDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author.firstName", is(topic.getAuthor().getFirstName())))
                .andExpect(jsonPath("$.title", is(topic.getTitle())))
                .andExpect(jsonPath("$.subjectType", is(topic.getSubjectType().toString())));
    }
    @Test
    void whenGETIsCalledWithInvalidIdThenReturnNotFound() throws Exception {
        when(topicService.findById(topicDTO.getId())).thenThrow(TopicNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_API+"/id/"+topicDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(topicDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGETIsCalledWithoutParamsThenReturnAllTopics() throws Exception {
        when(topicService.findByParams(param)).thenReturn(BuildMocks.buildListTopic());
        mockMvc.perform(MockMvcRequestBuilders.get(URL_API)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].title", is("Title 1")))
                .andExpect(jsonPath("$[1].description", is("Description 1")))
                .andExpect(jsonPath("$[9].title", is("Title 9")))
                .andExpect(jsonPath("$[9].description", is("Description 9")));
    }
    @Test
    void whenGETIsCalledWitParamsThenReturnAllTopics() throws Exception {
        param.put("q","Search");
        param.put("order","desc");
        param.put("sort","id");
        param.put("page","1");
        param.put("limit","10");
        when(topicService.findByParams(param)).thenReturn(BuildMocks.buildListTopic());
        mockMvc.perform(MockMvcRequestBuilders.get(URL_API+"?q=Search&order=desc&sort=id&page=1&limit=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].title", is("Title 1")))
                .andExpect(jsonPath("$[1].description", is("Description 1")))
                .andExpect(jsonPath("$[9].title", is("Title 9")))
                .andExpect(jsonPath("$[9].description", is("Description 9")));
    }


    private MessageResponseDTO getBuild(String message) {
        return MessageResponseDTO.builder()
                .message(message)
                .build();
    }
}
