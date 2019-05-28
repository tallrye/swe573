package com.tallrye.wlearn.controller;

import com.tallrye.wlearn.dto.AnswerRequestDto;
import com.tallrye.wlearn.dto.ApiResponseDto;
import com.tallrye.wlearn.dto.LearningStepsResponseDto;
import com.tallrye.wlearn.dto.QuestionRequestDto;
import com.tallrye.wlearn.security.CurrentUser;
import com.tallrye.wlearn.security.UserPrincipal;
import com.tallrye.wlearn.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "api/questions")
public class QuestionController {

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Transactional
    @PostMapping(value = "/")
    public ResponseEntity<ApiResponseDto> createQuestion(@CurrentUser UserPrincipal currentUser,
                                                         @Valid @RequestBody QuestionRequestDto questionRequestDto) {
        return questionService.createQuestionByContentId(currentUser, questionRequestDto);
    }

    @Transactional
    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponseDto> deleteQuestion(@CurrentUser UserPrincipal currentUser,
                                                         @PathVariable Long questionId) {
        return questionService.deleteQuestionById(questionId, currentUser);
    }

    @Transactional
    @GetMapping("/{contentId}")
    public ResponseEntity<LearningStepsResponseDto> getLearningPath(@CurrentUser UserPrincipal currentUser,
                                                                    @PathVariable Long contentId) {
        return questionService.getLearningSteps(currentUser, contentId);
    }

    @Transactional
    @PostMapping(value = "/answer/")
    public ResponseEntity<ApiResponseDto> answer(@CurrentUser UserPrincipal currentUser,
                                                 @Valid @RequestBody AnswerRequestDto answerRequestDto) {
        return questionService.giveAnswer(currentUser, answerRequestDto);
    }
}