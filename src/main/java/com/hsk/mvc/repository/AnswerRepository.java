package com.hsk.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hsk.mvc.data.AnswerEntity;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
}
