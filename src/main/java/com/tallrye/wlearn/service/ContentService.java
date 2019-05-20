package com.tallrye.wlearn.service;

import com.tallrye.wlearn.controller.dto.request.ContentRequest;
import com.tallrye.wlearn.controller.dto.response.ApiResponse;
import com.tallrye.wlearn.controller.dto.response.ContentResponse;
import com.tallrye.wlearn.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

public interface ContentService {

    ResponseEntity<ApiResponse> createContentByTopicId(UserPrincipal currentUser, ContentRequest contentRequest);

    ResponseEntity<ContentResponse> getContentById(UserPrincipal currentUser, Long contentId);

    ResponseEntity<ApiResponse> deleteContentById(UserPrincipal currentUser, Long contentId);

}
