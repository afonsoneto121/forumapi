package com.br.dio.forumapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TopicNotFoundException extends Exception{
    public TopicNotFoundException(Long id) {
        super("Topic not found with ID " + id);
    }
}
