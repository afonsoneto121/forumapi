package com.br.dio.forumapi.mapper;

import com.br.dio.forumapi.dto.request.PersonDTO;
import com.br.dio.forumapi.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);


    public Person toModel(PersonDTO personDTO);

    public PersonDTO toDTO(Person person);
}
