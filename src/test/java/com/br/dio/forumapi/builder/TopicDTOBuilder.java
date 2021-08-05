package com.br.dio.forumapi.builder;

import com.br.dio.forumapi.dto.request.AnswerDTO;
import com.br.dio.forumapi.dto.request.PersonDTO;
import com.br.dio.forumapi.dto.request.TopicDTO;
import com.br.dio.forumapi.enums.SubjectType;
import lombok.Builder;

import java.util.List;

@Builder
public class TopicDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String question = "What's callback";

    @Builder.Default
    private String dateCreation = "04-08-2023";

    @Builder.Default
    private boolean resolved = false;

    @Builder.Default
    private SubjectType subjectType = SubjectType.JS;

    @Builder.Default
    List<AnswerDTO> answers = null;

    @Builder.Default
    private PersonDTO author = PersonDTOBuilder.builder().build().toPersonTDO() ;

    public TopicDTO toTopicDTO() {
        return new TopicDTO(id,question,dateCreation,resolved,subjectType,answers,author);
    }
}
