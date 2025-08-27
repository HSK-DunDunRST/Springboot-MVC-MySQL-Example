package com.hsk.mvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hsk.mvc.data.AnswerEntity;
import com.hsk.mvc.data.QuestionEntity;
import com.hsk.mvc.data.dto.AnswerDTO;
import com.hsk.mvc.service.AnswerService;
import com.hsk.mvc.service.QuestionService;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    /* POST 방식의 답변 생성 처리 */
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Long id,
                                @Valid AnswerDTO answerDTO,
                                BindingResult bindingResult) {
        QuestionEntity questionEntity = this.questionService.getQuestion(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", questionEntity);
            return "question_detail";
        }
        AnswerEntity answerEntity = this.answerService.create(questionEntity,answerDTO.getContent());
        return String.format("redirect:/question/detail/%s#answer",answerEntity.getQuestion().getId());
    }

    /* GET 방식의 답변 수정 처리 */
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerDTO answerDTO, @PathVariable("id") Long id) {
        AnswerEntity answerEntity = this.answerService.getAnswer(id);
        answerDTO.setContent(answerEntity.getContent());
        return "answer_form";
    }

    /* POST 방식의 답변 수정 처리 */
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerDTO answerDTO, BindingResult bindingResult,
                                @PathVariable("id") Long id){
        if (bindingResult.hasErrors()){
            return "answer_form";
        }
        /* 답변 객체 생성 */
        AnswerEntity answerEntity = this.answerService.getAnswer(id);
        this.answerService.modify(answerEntity, answerDTO.getContent());
        return String.format("redirect:/question/detail/%s#answer",answerEntity.getQuestion().getId());
    }

    /* GET 방식 답변 삭제 처리 */
    @GetMapping("/delete/{id}")
    public String answerDelete(@PathVariable("id") Long id){
        AnswerEntity answerEntity = this.answerService.getAnswer(id);
        this.answerService.delete(answerEntity);
        return String.format("redirect:/question/detail/%s",answerEntity.getQuestion().getId());
    }
}
