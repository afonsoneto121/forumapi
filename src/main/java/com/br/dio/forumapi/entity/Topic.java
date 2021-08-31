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
    private String title;

    @Column (nullable = false)
    private String description;

    @Column (nullable = false)
    private LocalDate dateCreation;

    @Column(columnDefinition = "boolean default false")
    private boolean resolved;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private SubjectType subjectType;

    @OneToMany (fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    List<Answer> answers;

    @ManyToOne
    private Person author;

}
