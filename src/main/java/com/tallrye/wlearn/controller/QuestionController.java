package com.tallrye.wlearn.controller;

import com.tallrye.wlearn.controller.dto.request.AnswerRequest;
import com.tallrye.wlearn.controller.dto.request.QuestionRequest;
import com.tallrye.wlearn.controller.dto.response.ApiResponse;
import com.tallrye.wlearn.controller.dto.response.LearningStepsResponse;
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
    public ResponseEntity<ApiResponse> createQuestionByContentId(@CurrentUser UserPrincipal currentUser,
                                                                 @Valid @RequestBody QuestionRequest questionRequest) {
        return questionService.createQuestionByContentId(currentUser, questionRequest);
    }

    @Transactional
    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse> deleteQuestionById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long questionId) {
        return questionService.deleteQuestionById(questionId, currentUser);
    }

    @Transactional
    @GetMapping("/{contentId}")
    public ResponseEntity<LearningStepsResponse> getLearningStepsByContentId(@CurrentUser UserPrincipal currentUser,
                                                                             @PathVariable Long contentId) {
        return questionService.getLearningSteps(currentUser, contentId);
    }

    @Transactional
    @PostMapping(value = "/answer/")
    public ResponseEntity<ApiResponse> giveAnswer(@CurrentUser UserPrincipal currentUser,
            @Valid @RequestBody AnswerRequest answerRequest) {
        return questionService.giveAnswer(currentUser, answerRequest);
    }
}