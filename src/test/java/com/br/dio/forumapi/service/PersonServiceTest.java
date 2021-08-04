package com.br.dio.forumapi.service;

import com.br.dio.forumapi.builder.PersonDTOBuilder;
import com.br.dio.forumapi.dto.request.PersonDTO;
import com.br.dio.forumapi.dto.response.MessageResponseDTO;
import com.br.dio.forumapi.entity.Person;
import com.br.dio.forumapi.mapper.PersonMapper;
import com.br.dio.forumapi.repository.PersonRepository;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.* ;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    private PersonMapper personMapper =  PersonMapper.INSTANCE;

    @InjectMocks
    private PersonService personService;

    PersonDTO personDTO;
    Person expetedSavePerson;

    @BeforeAll
    void setup() {
        /* Given */
        personDTO = PersonDTOBuilder.builder().build().toPersonTDO();
        expetedSavePerson = personMapper.toModel(personDTO);
    }
    @Test
    void whenPersonInformedThenPersonShoulBeCreated(){


        /* When */
        when(personRepository.save(expetedSavePerson)).thenReturn(expetedSavePerson);

        /* Then */
        MessageResponseDTO responseDTO = personService.createPerson(personDTO);

        assertThat(responseDTO,
                Matchers.is(Matchers.equalTo(getBuild("Person created with ID " + expetedSavePerson.getId()))));
    }

    @Test
    void whenPerson() {
    }

    private MessageResponseDTO getBuild(String message) {
        return MessageResponseDTO.builder()
                .message(message)
                .build();
    }
}
