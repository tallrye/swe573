package com.tallrye.wlearn.service;

import com.tallrye.wlearn.controller.dto.request.ChoiceRequest;
import com.tallrye.wlearn.controller.dto.response.ApiResponse;
import com.tallrye.wlearn.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface ChoiceService {

    ResponseEntity<ApiResponse> createChoiceByQuestionId(UserPrincipal currentUser,
                                                         ChoiceRequest choiceRequest);

    ResponseEntity<ApiResponse> deleteChoiceById(UserPrincipal currentUser, Long choiceId);
}
