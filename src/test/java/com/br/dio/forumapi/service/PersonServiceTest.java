package com.br.dio.forumapi.service;

import com.br.dio.forumapi.builder.PersonDTOBuilder;
import com.br.dio.forumapi.dto.request.PersonDTO;
import com.br.dio.forumapi.dto.response.MessageResponseDTO;
import com.br.dio.forumapi.entity.Person;
import com.br.dio.forumapi.exception.PersonAlreadyRegisteredException;
import com.br.dio.forumapi.exception.PersonNotFoundException;
import com.br.dio.forumapi.mapper.PersonMapper;
import com.br.dio.forumapi.repository.PersonRepository;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    private PersonMapper personMapper =  PersonMapper.INSTANCE;

    @InjectMocks
    private PersonService personService;

    PersonDTO personDTO;
    Person person;

    @BeforeEach
    void setup() {
        personDTO = PersonDTOBuilder.builder().build().toPersonTDO();
        person = personMapper.toModel(personDTO);
    }
    @Test
    void whenPersonInformedThenPersonShoulBeCreated() throws PersonAlreadyRegisteredException {

        /* When */
        when(personRepository.findByEmail(person.getEmail())).thenReturn(Optional.empty());
        when(personRepository.save(person)).thenReturn(person);

        /* Then */
        MessageResponseDTO responseDTO = personService.createPerson(personDTO);

        assertThat(responseDTO,
                Matchers.is(Matchers.equalTo(getBuild("Person created with ID " + person.getId()))));
    }

    @Test
    void whenAlreadyRegisteredPersonThenAnExceptionShouldBeThrown() {

        when(personRepository.findByEmail(person.getEmail())).thenReturn(Optional.of(person));

        assertThrows(PersonAlreadyRegisteredException.class, () -> personService.createPerson(personDTO));


    }

    @Test
    void whenValidIdPersonThenReturnPerson() throws PersonNotFoundException {

        when(personRepository.findById(personDTO.getId())).thenReturn(Optional.of(person));

        PersonDTO personFound = personService.findById(personDTO.getId());
        assertThat(personFound, Matchers.is(Matchers.equalTo(personDTO)));
    }
    @Test
    void whenInvalidIdPersonThenAExceptionIsThrown()  {

        when(personRepository.findById(personDTO.getId())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.findById(personDTO.getId()));
    }

    @Test
    void whenListPersonIsCalledThenAListOfPersonIsReturned() {
        when(personRepository.findAll()).thenReturn(Collections.singletonList(person));

        List<PersonDTO> personDTOList = personService.listAll();

        assertThat(personDTOList, Matchers.is(Matchers.not(Matchers.empty())));
        assertThat(personDTOList.get(0), Matchers.is(Matchers.equalTo(personDTO)));

    }
    @Test
    void whenListPersonIsCalledThenAEmptyListIsReturned() {
        when(personRepository.findAll()).thenReturn(Collections.emptyList());

        List<PersonDTO> personDTOList = personService.listAll();

        assertThat(personDTOList, Matchers.is(Matchers.empty()));

    }

    @Test
    void whenExclusionPersonIsCalledWithValidIdThenAPersonShouldBeDeleted() throws PersonNotFoundException {
        when(personRepository.findById(personDTO.getId())).thenReturn(Optional.of(person));
        doNothing().when(personRepository).deleteById(personDTO.getId());

        personService.delete(personDTO.getId());
        verify(personRepository, times(1)).findById(personDTO.getId());
        verify(personRepository, times(1)).deleteById(personDTO.getId());
    }
    @Test
    void whenExclusionPersonIsCalledWithInvalidIdThenAExceptionIsThrown() {
        when(personRepository.findById(personDTO.getId())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.delete(personDTO.getId()));
    }


    private MessageResponseDTO getBuild(String message) {
        return MessageResponseDTO.builder()
                .message(message)
                .build();
    }
}
