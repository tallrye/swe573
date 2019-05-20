package com.tallrye.wlearn.service;

import com.tallrye.wlearn.controller.dto.response.UserIdentityAvailability;
import com.tallrye.wlearn.controller.dto.response.UserProfile;
import com.tallrye.wlearn.controller.dto.response.UserSummary;
import com.tallrye.wlearn.security.UserPrincipal;

public interface UserService {

    UserSummary getCurrentUser(UserPrincipal currentUser);

    UserIdentityAvailability checkUsernameAvailability(String email);

    UserProfile getUserProfile(String username);
}
