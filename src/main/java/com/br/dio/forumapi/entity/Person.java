package com.br.dio.forumapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany (mappedBy = "author", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Topic> topics;

    @OneToMany (mappedBy = "author", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Answer> answers;
}
