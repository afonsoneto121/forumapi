package com.br.dio.forumapi.repository;

import com.br.dio.forumapi.entity.Topic;
import com.br.dio.forumapi.enums.SubjectType;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT t FROM Topic t WHERE t.title LIKE %?1%  OR t.description LIKE %?1%")
    List<Topic> findByValueQ(String valuesQ, Pageable pageable);

    @Query("SELECT t FROM Topic t WHERE t.title LIKE %?1%  AND t.description LIKE %?2%")
    List<Topic> findByTitleAndDescription(String title, String description, Pageable pageRequest);

    @Query("SELECT t FROM Topic t WHERE t.title LIKE %?1% ")
    List<Topic> findByTitle(String title, Pageable pageRequest);

    @Query("SELECT t FROM Topic t WHERE t.description LIKE %?1%")
    List<Topic> findByDescription(String title, Pageable pageRequest);

}
