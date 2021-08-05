package com.br.dio.forumapi.service;

import com.br.dio.forumapi.dto.request.PersonDTO;
import com.br.dio.forumapi.dto.response.MessageResponseDTO;
import com.br.dio.forumapi.entity.Person;
import com.br.dio.forumapi.exception.PersonAlreadyRegisteredException;
import com.br.dio.forumapi.exception.PersonNotFoundException;
import com.br.dio.forumapi.mapper.PersonMapper;
import com.br.dio.forumapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private PersonRepository personRepository;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO) throws PersonAlreadyRegisteredException {
        Person personToSave = personMapper.toModel(personDTO);
        verifyIfIsAlreadyRegistered(personToSave.getEmail());
        Person personSaved = personRepository.save(personToSave);
        return getBuild("Person created with ID " + personSaved.getId());
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream().map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    private Person verifyIfExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() ->  new PersonNotFoundException(id));

    }
    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyIfExists(id);

        return personMapper.toDTO(person);
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyIfExists(id);
        personRepository.deleteById(id);

    }

    public MessageResponseDTO updateById(Long id, PersonDTO personDTO) throws PersonNotFoundException, PersonAlreadyRegisteredException {
        verifyIfExists(id);
        verifyIfIsAlreadyRegistered(personDTO.getEmail());
        Person personToUpdate = personMapper.toModel(personDTO);

        personRepository.save(personToUpdate);
        return getBuild("Person updated with ID " + id);
    }

    private MessageResponseDTO getBuild(String message) {
        return MessageResponseDTO.builder()
                .message(message)
                .build();
    }
    private void verifyIfIsAlreadyRegistered(String email) throws PersonAlreadyRegisteredException {
        Optional<Person> optionalPerson = personRepository.findByEmail(email);
        if(optionalPerson.isPresent()) {
            throw new PersonAlreadyRegisteredException(optionalPerson.get().getFirstName());
        }
    }
}
