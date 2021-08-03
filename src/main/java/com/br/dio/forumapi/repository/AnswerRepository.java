package com.br.dio.forumapi.repository;

import com.br.dio.forumapi.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
}
