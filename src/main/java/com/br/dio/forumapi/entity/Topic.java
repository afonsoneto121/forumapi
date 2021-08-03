package com.br.dio.forumapi.entity;

import com.br.dio.forumapi.enums.SubjectType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Topic {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String question;

    @Column (nullable = false)
    private LocalDate dateCreation;

    @Column(columnDefinition = "boolean default true")
    private boolean resolved;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private SubjectType subjectType;

    @OneToMany (mappedBy = "topic")
    List<Answer> answers;

    @ManyToOne
    private Person author;

}
