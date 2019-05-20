package com.tallrye.wlearn.service;


import com.tallrye.wlearn.TestUtils;
import com.tallrye.wlearn.controller.dto.response.UserIdentityAvailability;
import com.tallrye.wlearn.controller.dto.response.UserProfile;
import com.tallrye.wlearn.controller.dto.response.UserSummary;
import com.tallrye.wlearn.exception.ResourceNotFoundException;
import com.tallrye.wlearn.persistence.TopicRepository;
import com.tallrye.wlearn.persistence.UserRepository;
import com.tallrye.wlearn.persistence.model.User;
import com.tallrye.wlearn.service.implementation.UserServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTest extends AbstractServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private final UserService sut = new UserServiceImpl(userRepository, topicRepository);


    @Test
    public void testGetCurrentUser() {
        //Test
        final UserSummary summary = sut.getCurrentUser(currentUser);

        //Verify
        assertEquals(summary.getId(), currentUser.getId());
        assertEquals(summary.getUsername(), currentUser.getUsername());
        assertEquals(summary.getName(), currentUser.getName());
    }

    @Test
    public void testCheckUsernameAvailability_NotAvailable() {
        //Prepare
        when(userRepository.existsByEmail("email")).thenReturn(true);
        //Test
        final UserIdentityAvailability availability = sut.checkUsernameAvailability("email");
        //Verify
        assertEquals(availability.getAvailable(), false);
    }

    @Test
    public void testCheckUsernameAvailability_Available() {
        //Prepare
        when(userRepository.existsByEmail("email")).thenReturn(false);
        //Test
        final UserIdentityAvailability availability = sut.checkUsernameAvailability("email");
        //Verify
        assertEquals(availability.getAvailable(), true);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetUserProfile_NotFound() {
        //Prepare
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
        //Test
        sut.getUserProfile("username");
    }

    @Test
    public void testGetUserProfile_Success() {
        //Prepare
        final User user = TestUtils.createDummyUser();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(topicRepository.countByCreatedBy(user.getId())).thenReturn(1L);
        //Test
        final UserProfile userprofile =sut.getUserProfile(user.getUsername());
        //Verify
        assertEquals(userprofile.getId(),user.getId());
        assertEquals(userprofile.getName(),user.getName());
        assertEquals(userprofile.getUsername(),user.getUsername());
        assertEquals(userprofile.getJoinedAt(),user.getCreatedAt());
        assertEquals(userprofile.getTopicCount().longValue(),1L);
    }
}
