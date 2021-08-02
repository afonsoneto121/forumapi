package com.br.dio.forumapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/people")
public class PersonController {

    @GetMapping
    public String getBook() {
        return "API Test";
    }
}
