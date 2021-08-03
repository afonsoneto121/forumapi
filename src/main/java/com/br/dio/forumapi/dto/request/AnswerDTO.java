package com.br.dio.forumapi.dto.request;

import com.br.dio.forumapi.entity.Person;
import com.br.dio.forumapi.entity.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private Long id;

    @NotBlank
    private PersonDTO author;

    @NotBlank
    private String answer;

    private TopicDTO topic;
}
