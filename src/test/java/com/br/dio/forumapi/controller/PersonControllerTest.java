package com.br.dio.forumapi.controller;

import com.br.dio.forumapi.builder.PersonDTOBuilder;
import com.br.dio.forumapi.dto.request.PersonDTO;
import com.br.dio.forumapi.dto.response.MessageResponseDTO;
import com.br.dio.forumapi.entity.Person;

import com.br.dio.forumapi.exception.PersonNotFoundException;
import com.br.dio.forumapi.mapper.PersonMapper;
import com.br.dio.forumapi.service.PersonService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.br.dio.forumapi.utils.JsonConvertionUtils.asJsonString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {
    private static final String API_URL = "/api/v1/people";

    private MockMvc mockMvc;

    private Person person;

    private PersonDTO personDTO;
    private PersonMapper personMapper =  PersonMapper.INSTANCE;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
        personDTO = PersonDTOBuilder.builder().build().toPersonTDO();
        person = personMapper.toModel(personDTO);
    }

    @Test
    void whenPOSTisCalledThenAPersonIsCreated() throws Exception {

        when(personService.createPerson(personDTO)).thenReturn(getBuild("Person created with ID " + person.getId()));

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(getBuild("Person created with ID " + person.getId()))));
    }

    private MessageResponseDTO getBuild(String message) {
        return MessageResponseDTO.builder()
                .message(message)
                .build();
    }

    @Test
    void whenPOSTisInvalidCalledThenAErrorIsReturned() throws Exception {
        personDTO.setFirstName("");

        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithValidIdPersonThenReturnPerson() throws Exception {

        when(personService.findById(personDTO.getId())).thenReturn(personDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL+"/"+personDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is(personDTO.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is(personDTO.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(personDTO.getEmail())));
    }
    @Test
    void whenGETIsCalledWithInvalidIdPersonThenReturnPerson() throws Exception {

        when(personService.findById(personDTO.getId())).thenThrow(PersonNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL+"/"+personDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());    
    
    }

    @Test
    void whenGETListOfPeopleIsCalledThenReturnAllPeople() throws Exception {

        when(personService.listAll()).thenReturn(Collections.singletonList(personDTO));

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is(personDTO.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is(personDTO.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is(personDTO.getEmail())));
    }

    @Test
    void whenGETListEmptyIsCalledThenReturnEmpty() throws Exception {

        when(personService.listAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void whenDELETEIsCalledWithValidIdThenContentStatusIsReturned() throws Exception {

        doNothing().when(personService).delete(personDTO.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL+"/"+personDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundIsReturned() throws Exception {

        doThrow(PersonNotFoundException.class).when(personService).delete(personDTO.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL+"/"+personDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void whenPUTIsCalledWithValidIdThenUpdatedPerson() throws Exception {
        when(personService.updateById(personDTO.getId(),personDTO)).thenReturn(getBuild("Person updated with ID " + personDTO.getId()));

        mockMvc.perform(MockMvcRequestBuilders.put(API_URL+"/"+personDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(personDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",Matchers.is("Person updated with ID " + personDTO.getId())));

    }

    @Test
    void whenPUTIsCalledWithInvalidIdThenNotFoundIsReturned() throws Exception {

        when(personService.updateById(personDTO.getId(), personDTO)).thenThrow(PersonNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL+"/"+personDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(personDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }
}
