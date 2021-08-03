package com.br.dio.forumapi.repository;

import com.br.dio.forumapi.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
