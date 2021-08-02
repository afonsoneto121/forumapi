package com.br.dio.forumapi.service;

import com.br.dio.forumapi.dto.MessageResponseDTO;
import com.br.dio.forumapi.entity.Person;
import com.br.dio.forumapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(Person person) {
        Person personSaved = personRepository.save(person);
        return MessageResponseDTO.builder()
                .message("Person created with ID " + personSaved.getId())
                .build();
    }
}
