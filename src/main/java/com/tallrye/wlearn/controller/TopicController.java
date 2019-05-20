package com.tallrye.wlearn.controller;

import com.tallrye.wlearn.controller.dto.request.EnrollmentRequest;
import com.tallrye.wlearn.controller.dto.request.PublishRequest;
import com.tallrye.wlearn.controller.dto.request.TopicRequest;
import com.tallrye.wlearn.controller.dto.response.ApiResponse;
import com.tallrye.wlearn.controller.dto.response.TopicResponse;
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
    public ResponseEntity<List<TopicResponse>> getAllTopics(@CurrentUser UserPrincipal currentUser) {
        return topicService.getAllTopics(currentUser);
    }

    @Transactional
    @GetMapping(value = "/{username}")
    public ResponseEntity<List<TopicResponse>> getTopicsByUsername(@PathVariable String username,
            @CurrentUser UserPrincipal currentUser) {
        return topicService.getTopicsCreatedBy(username, currentUser);
    }

    @Transactional
    @GetMapping(value = "/topic/{topicId}")
    public ResponseEntity<TopicResponse> getTopicById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long topicId) {
        return topicService.getTopicById(topicId, currentUser);
    }

    @Transactional
    @PostMapping(value = "/publish")
    public ResponseEntity<ApiResponse> publishStatusUpdate(@CurrentUser UserPrincipal currentUser,
                                                           @RequestBody PublishRequest publishRequest) {
        return topicService.publishStatusUpdate(currentUser, publishRequest);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<TopicResponse> createTopic(@CurrentUser UserPrincipal currentUser,
            @Valid @RequestBody TopicRequest topicRequest) {
        return topicService.createTopic(currentUser, topicRequest);
    }

    @Transactional
    @DeleteMapping(value = "/topic/{topicId}")
    public ResponseEntity<ApiResponse> deleteTopicById(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long topicId) {
        return topicService.deleteTopicById(topicId, currentUser);
    }

    @Transactional
    @PostMapping("/enroll")
    public ResponseEntity<ApiResponse> enrollToTopicByUsername(@CurrentUser UserPrincipal currentUser,
            @RequestBody EnrollmentRequest enrollmentRequest) {
        return topicService.enrollToTopicByUsername(currentUser, enrollmentRequest);
    }

    @Transactional
    @GetMapping("/enrolled/{userId}")
    public ResponseEntity<List<TopicResponse>> getEnrolledTopics(@CurrentUser UserPrincipal currentUser,
            @PathVariable Long userId) {
        return topicService.getTopicsByEnrolledUserId(currentUser, userId);
    }
}
