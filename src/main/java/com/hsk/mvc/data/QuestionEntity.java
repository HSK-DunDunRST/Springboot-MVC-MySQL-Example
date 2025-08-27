package com.hsk.mvc.data;
import java.util.List;

import com.hsk.mvc.data.common.BaseTimeEntity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Getter
@Setter
@Entity
public class QuestionEntity extends BaseTimeEntity{
    
    /* 질문 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 질문 제목 */
    @Column(length = 200)
    private String subject;
    
    /* 질문 내용 */
    @Column(columnDefinition = "TEXT")
    private String content;

    /* 질문에 해당되는 대답 id */
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<AnswerEntity> answerList;
}
