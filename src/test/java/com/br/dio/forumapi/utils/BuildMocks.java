package com.br.dio.forumapi.utils;

import com.br.dio.forumapi.entity.Topic;
import com.br.dio.forumapi.enums.SubjectType;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuildMocks {
    public static List<Topic> buildListTopic() {
        List<Topic> list = new ArrayList<>();
        for (long i = 0L; i < 10;i++) {
            list.add(Topic.builder()
                    .id(i)
                    .title("Title "+i)
                    .description("Description "+i)
                    .resolved(false)
                    .subjectType(SubjectType.JAVA)
                    .dateCreation(LocalDate.of(2021,8,10))
                    .answers(null)
                    .author(null)
                    .build());
        }
        return list;
    }

}
