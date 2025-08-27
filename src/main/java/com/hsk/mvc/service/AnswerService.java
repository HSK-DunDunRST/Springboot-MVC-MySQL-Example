package com.hsk.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.hsk.mvc.data.AnswerEntity;
import com.hsk.mvc.data.QuestionEntity;
import com.hsk.mvc.exception.DataNotFoundException;
import com.hsk.mvc.repository.AnswerRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    /* 답변글 생성하는 서비스 */
    public AnswerEntity create(QuestionEntity questionEntity, String content) {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setContent(content);
        answerEntity.setQuestion(questionEntity);
        this.answerRepository.save(answerEntity);
        return answerEntity;
    }

    /* ID로 답변 조회하기 */
    public AnswerEntity getAnswer(Long id){
        Optional<AnswerEntity> answer = this.answerRepository.findById(id);
        
        if (answer.isPresent()){
            return answer.get();
        } else {
            throw new DataNotFoundException("데이터를 조회할 수 없어요.");
        }
    }

    /* 답변 수정 서비스 */
    public void modify(AnswerEntity answerEntity, String content) {
        answerEntity.setContent(content);
        this.answerRepository.save(answerEntity);
    }
    /* 답변 삭제 서비스 */
    public void delete(AnswerEntity answerEntity){
        this.answerRepository.delete(answerEntity);
    }
}
