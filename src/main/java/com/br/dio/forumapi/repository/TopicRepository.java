package com.br.dio.forumapi.repository;

import com.br.dio.forumapi.entity.Topic;
import com.br.dio.forumapi.enums.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("SELECT t FROM Topic t WHERE t.subjectType = ?1")
    List<Topic> findBySubject(SubjectType subjectType);

    @Query("SELECT t FROM Topic t WHERE t.resolved = true")
    List<Topic> findByResolved();

    @Query("SELECT t FROM Topic t WHERE t.resolved = false")
    List<Topic> findByUnResolved();

}
