package com.tallrye.wlearn.controller;

import com.tallrye.wlearn.dto.UserIdentityAvailabilityDto;
import com.tallrye.wlearn.dto.UserProfileDto;
import com.tallrye.wlearn.dto.UserSummaryDto;
import com.tallrye.wlearn.security.CurrentUser;
import com.tallrye.wlearn.security.UserPrincipal;
import com.tallrye.wlearn.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @GetMapping(value = "/user/me")
    public UserSummaryDto getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return userService.getCurrentUser(currentUser);
    }

    @Transactional
    @GetMapping(value = "/user/checkUsernameAvailability")
    public UserIdentityAvailabilityDto checkUsernameAvailability(@RequestParam(value = "email") String email) {
        return userService.checkUsernameAvailability(email);
    }

    @Transactional
    @GetMapping(value = "/users/{username}")
    public UserProfileDto getUserProfile(@PathVariable(value = "username") String username) {

        return userService.getUserProfile(username);
    }

}
