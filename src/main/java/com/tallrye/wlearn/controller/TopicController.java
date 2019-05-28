package com.tallrye.wlearn.controller;

import com.tallrye.wlearn.dto.*;
import com.tallrye.wlearn.security.CurrentUser;
import com.tallrye.wlearn.security.UserPrincipal;
import com.tallrye.wlearn.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/topics")
public class TopicController {

    private TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @Transactional
    @GetMapping
    public ResponseEntity<List<TopicResponseDto>> getAllTopics(@CurrentUser UserPrincipal currentUser) {
        return topicService.getAllTopics(currentUser);
    }

    @Transactional
    @GetMapping(value = "/{username}")
    public ResponseEntity<List<TopicResponseDto>> getTopicsByUsername(@PathVariable String username,
                                                                      @CurrentUser UserPrincipal currentUser) {
        return topicService.getTopicsCreatedBy(username, currentUser);
    }

    @Transactional
    @GetMapping(value = "/topic/{topicId}")
    public ResponseEntity<TopicResponseDto> getTopic(@CurrentUser UserPrincipal currentUser,
                                                     @PathVariable Long topicId) {
        return topicService.getTopicById(topicId, currentUser);
    }

    @Transactional
    @PostMapping(value = "/publish")
    public ResponseEntity<ApiResponseDto> changeStatus(@CurrentUser UserPrincipal currentUser,
                                                       @RequestBody PublishRequestDto publishRequestDto) {
        return topicService.publishStatusUpdate(currentUser, publishRequestDto);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<TopicResponseDto> createTopic(@CurrentUser UserPrincipal currentUser,
                                                        @Valid @RequestBody TopicRequestDto topicRequestDto) {
        return topicService.createTopic(currentUser, topicRequestDto);
    }

    @Transactional
    @DeleteMapping(value = "/topic/{topicId}")
    public ResponseEntity<ApiResponseDto> deleteTopic(@CurrentUser UserPrincipal currentUser,
                                                      @PathVariable Long topicId) {
        return topicService.deleteTopicById(topicId, currentUser);
    }

    @Transactional
    @PostMapping("/enroll")
    public ResponseEntity<ApiResponseDto> enroll(@CurrentUser UserPrincipal currentUser,
                                                 @RequestBody EnrollmentRequestDto enrollmentRequestDto) {
        return topicService.enrollToTopicByUsername(currentUser, enrollmentRequestDto);
    }

    @Transactional
    @GetMapping("/enrolled/{userId}")
    public ResponseEntity<List<TopicResponseDto>> getEnrolledTopics(@CurrentUser UserPrincipal currentUser,
                                                                    @PathVariable Long userId) {
        return topicService.getTopicsByEnrolledUserId(currentUser, userId);
    }
}
