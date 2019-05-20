package com.tallrye.wlearn.controller;


import com.tallrye.wlearn.controller.dto.request.ContentRequest;
import com.tallrye.wlearn.controller.dto.response.ApiResponse;
import com.tallrye.wlearn.controller.dto.response.ContentResponse;
import com.tallrye.wlearn.security.CurrentUser;
import com.tallrye.wlearn.security.UserPrincipal;
import com.tallrye.wlearn.service.ContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/api/contents")
public class ContentController {

    private ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @Transactional
    @PostMapping(value = "/")
    public ResponseEntity<ApiResponse> createContentByTopicId(@CurrentUser UserPrincipal currentUser,
                                                              @Valid @RequestBody ContentRequest contentRequest) {
        return contentService.createContentByTopicId(currentUser, contentRequest);
    }

    @Transactional
    @GetMapping(value = "/{contentId}")
    public ResponseEntity<ContentResponse> getContentById(@CurrentUser UserPrincipal currentUser,
                                                          @PathVariable Long contentId) {
        return contentService.getContentById(currentUser, contentId);
    }

    @Transactional
    @DeleteMapping(value = "/{contentId}")
    public ResponseEntity<ApiResponse> deleteContentById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long contentId) {
        return contentService.deleteContentById(currentUser, contentId);
    }

}
