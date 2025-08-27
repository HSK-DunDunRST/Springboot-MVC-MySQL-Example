package com.hsk.mvc.repository;

import java.util.List;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.hsk.mvc.data.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity,Long>, JpaSpecificationExecutor<QuestionEntity> {

    QuestionEntity findBySubject(String subject);
    QuestionEntity findBySubjectAndContent(String subject, String content);
    List<QuestionEntity> findBySubjectLike(String subject);
}
