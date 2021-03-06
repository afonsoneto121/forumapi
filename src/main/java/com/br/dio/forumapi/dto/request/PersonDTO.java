package com.br.dio.forumapi.dto.request;

import com.br.dio.forumapi.entity.Answer;
import com.br.dio.forumapi.entity.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    private Long id;
    @NotBlank(message = "firstName inválido")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank
    @Email(message = "Email inválido")
    private String email;

    private Set<TopicDTO> topics;

    private Set<AnswerDTO> answers;
}
