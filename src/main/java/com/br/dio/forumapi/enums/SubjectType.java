package com.br.dio.forumapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubjectType {
    JAVA("Java"),
    PYTHON("Python"),
    JS("JavaScript"),
    REACTJS("ReacttJS"),
    MATH("Math"),
    LOGIC("Logic");
    private final String description;
}
