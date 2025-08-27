package com.hsk.mvc.data;

import com.hsk.mvc.data.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AnswerEntity extends BaseTimeEntity {

    /* 답변 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /* 답변 내용 */
    @Column(columnDefinition = "TEXT")
    private String content;

    /* 답변에 해당되는 질문 id */
    @ManyToOne
    private QuestionEntity question;

}
