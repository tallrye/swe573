package com.wlearn.backend.controller;

import com.wlearn.backend.exception.ResourceNotFoundException;
import com.wlearn.backend.model.User;
import com.wlearn.backend.payload.*;
import com.wlearn.backend.repository.TopicRepository;
import com.wlearn.backend.repository.UserRepository;
import com.wlearn.backend.security.CurrentUser;
import com.wlearn.backend.security.UserPrincipal;
import com.wlearn.backend.service.TopicService;
import com.wlearn.backend.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicService topicService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser){
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "email") String email){
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long topicCount = topicRepository.countByCreatedBy(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), topicCount);

        return userProfile;
    }

    @GetMapping("/users/{username}/topics")
    public PagedResponse<TopicResponse> getTopicsCreatedBy(@PathVariable(value = "username") String username,
                                                           @CurrentUser UserPrincipal currentUser,
                                                           @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return topicService.getTopicsCreatedBy(username, currentUser, page, size);
    }

}
