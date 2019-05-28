package com.tallrye.wlearn.service;

import com.tallrye.wlearn.dto.UserIdentityAvailabilityDto;
import com.tallrye.wlearn.dto.UserProfileDto;
import com.tallrye.wlearn.dto.UserSummaryDto;
import com.tallrye.wlearn.exception.ResourceNotFoundException;
import com.tallrye.wlearn.repository.TopicRepository;
import com.tallrye.wlearn.repository.UserRepository;
import com.tallrye.wlearn.entity.UserEntity;
import com.tallrye.wlearn.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {


    private UserRepository userRepository;

    private TopicRepository topicRepository;

    public UserService(UserRepository userRepository, TopicRepository topicRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    public UserSummaryDto getCurrentUser(UserPrincipal currentUser) {
        return new UserSummaryDto(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    }

    public UserIdentityAvailabilityDto checkUsernameAvailability(String email) {
        return new UserIdentityAvailabilityDto(!userRepository.existsByEmail(email));
    }

    public UserProfileDto getUserProfile(String username) {
        final UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "username", username));

        final long topicCount = topicRepository.countByCreatedBy(userEntity.getId());

        return new UserProfileDto(userEntity.getId(), userEntity.getUsername(), userEntity.getName(), userEntity.getCreatedAt(),
                topicCount);
    }
}
