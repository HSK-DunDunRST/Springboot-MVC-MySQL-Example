package com.hsk.mvc.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.hsk.mvc.data.AnswerEntity;
import com.hsk.mvc.data.QuestionEntity;
import com.hsk.mvc.exception.DataNotFoundException;
import com.hsk.mvc.repository.QuestionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    /* 질문 리스트 불러오기 */
    public Page<QuestionEntity> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        
        if (kw == null || kw.trim().isEmpty()) {
            return this.questionRepository.findAll(pageable);
        } else {
            Specification<QuestionEntity> spec = search(kw);
            return this.questionRepository.findAll(spec, pageable);
        }
    }

    /* 검색 조건 생성 */
    @SuppressWarnings("null")
    private Specification<QuestionEntity> search(String kw) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);  // 중복 제거
            Join<QuestionEntity, AnswerEntity> answerJoin = root.join("answerList", JoinType.LEFT);
            return criteriaBuilder.or(
                criteriaBuilder.like(root.get("subject"), "%" + kw + "%"), // 제목
                criteriaBuilder.like(root.get("content"), "%" + kw + "%"), // 내용
                criteriaBuilder.like(answerJoin.get("content"), "%" + kw + "%") // 답변 내용
            );
        };
    }

    /* id로 질문 불러오기 */
    public QuestionEntity getQuestion(Long id) {
        Optional<QuestionEntity> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("데이터를 찾을 수 없어요.");
        }
    }

    /* 질문 등록 서비스 */
    public void create(String subject, String content) {
        QuestionEntity question = new QuestionEntity();
        question.setSubject(subject);
        question.setContent(content);
        this.questionRepository.save(question);
    }

    /* 질문 내용 수정 서비스 */
    public void modify(QuestionEntity questionEntity, String subject, String content){
        questionEntity.setSubject(subject);
        questionEntity.setContent(content);
        this.questionRepository.save(questionEntity);
    }

    /* 질문 내용 삭제 서비스 */
    public void delete(QuestionEntity questionEntity){
        this.questionRepository.delete(questionEntity);
    }
}
