package com.tallrye.wlearn.service;

import com.tallrye.wlearn.controller.dto.request.AnswerRequest;
import com.tallrye.wlearn.controller.dto.request.QuestionRequest;
import com.tallrye.wlearn.controller.dto.response.ApiResponse;
import com.tallrye.wlearn.controller.dto.response.LearningStepsResponse;
import com.tallrye.wlearn.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface QuestionService {

    ResponseEntity<ApiResponse> createQuestionByContentId(UserPrincipal currentUser, QuestionRequest questionRequest);

    ResponseEntity<ApiResponse> deleteQuestionById(Long questionId, UserPrincipal currentUser);

    ResponseEntity<LearningStepsResponse> getLearningSteps(UserPrincipal currentUser, Long contentId);

    ResponseEntity<ApiResponse> giveAnswer(UserPrincipal currentUser, AnswerRequest answerRequest);

}
