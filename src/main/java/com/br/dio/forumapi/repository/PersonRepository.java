package com.br.dio.forumapi.repository;

import com.br.dio.forumapi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
