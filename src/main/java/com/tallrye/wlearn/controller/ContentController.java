package com.tallrye.wlearn.controller;


import com.tallrye.wlearn.dto.ApiResponseDto;
import com.tallrye.wlearn.dto.ContentRequestDto;
import com.tallrye.wlearn.dto.ContentResponseDto;
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
    public ResponseEntity<ApiResponseDto> createContent(@CurrentUser UserPrincipal currentUser,
                                                        @Valid @RequestBody ContentRequestDto contentRequestDto) {
        return contentService.createContentByTopicId(currentUser, contentRequestDto);
    }

    @Transactional
    @GetMapping(value = "/{contentId}")
    public ResponseEntity<ContentResponseDto> getContent(@CurrentUser UserPrincipal currentUser,
                                                         @PathVariable Long contentId) {
        return contentService.getContentById(currentUser, contentId);
    }

    @Transactional
    @DeleteMapping(value = "/{contentId}")
    public ResponseEntity<ApiResponseDto> deleteContent(@CurrentUser UserPrincipal currentUser,
                                                        @PathVariable Long contentId) {
        return contentService.deleteContentById(currentUser, contentId);
    }

}
