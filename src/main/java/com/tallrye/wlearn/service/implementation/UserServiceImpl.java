package com.tallrye.wlearn.service.implementation;

import com.tallrye.wlearn.controller.dto.response.UserIdentityAvailability;
import com.tallrye.wlearn.controller.dto.response.UserProfile;
import com.tallrye.wlearn.controller.dto.response.UserSummary;
import com.tallrye.wlearn.exception.ResourceNotFoundException;
import com.tallrye.wlearn.persistence.TopicRepository;
import com.tallrye.wlearn.persistence.UserRepository;
import com.tallrye.wlearn.persistence.model.User;
import com.tallrye.wlearn.security.UserPrincipal;
import com.tallrye.wlearn.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    private TopicRepository topicRepository;

    public UserServiceImpl(UserRepository userRepository, TopicRepository topicRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public UserSummary getCurrentUser(UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    }

    @Override
    public UserIdentityAvailability checkUsernameAvailability(String email) {
        return new UserIdentityAvailability(!userRepository.existsByEmail(email));
    }

    @Override
    public UserProfile getUserProfile(String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "username", username));

        final long topicCount = topicRepository.countByCreatedBy(user.getId());

        return new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(),
                topicCount);
    }
}
