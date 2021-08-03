package com.br.dio.forumapi.dto.request;

import com.br.dio.forumapi.enums.SubjectType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
    private Long id;

    @NotBlank
    @Size(min = 2, max = 1000)
    private String question;

    private String dateCreation;

    private boolean resolved;

    @Enumerated(EnumType.STRING)
    private SubjectType subjectType;

    List<AnswerDTO> answers;

    @NotNull
    @Valid
    private PersonDTO author;

}
