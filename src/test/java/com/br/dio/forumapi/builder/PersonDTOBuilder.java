package com.br.dio.forumapi.builder;

import com.br.dio.forumapi.dto.request.AnswerDTO;
import com.br.dio.forumapi.dto.request.PersonDTO;
import com.br.dio.forumapi.dto.request.TopicDTO;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Builder
public class PersonDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String firstName = "Afonso";

    @Builder.Default
    private String lastName = "Neto";

    @Builder.Default
    private String email = "teste@teste.com";

    @Builder.Default
    private Set<TopicDTO> topics = new HashSet<>();

    @Builder.Default
    private Set<AnswerDTO> answers= new HashSet<>();

    public PersonDTO toPersonTDO() {
        return new PersonDTO(id,firstName,lastName,email,topics,answers);
    }
}
