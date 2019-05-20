package com.tallrye.wlearn.controller;

import com.tallrye.wlearn.controller.dto.response.UserIdentityAvailability;
import com.tallrye.wlearn.controller.dto.response.UserProfile;
import com.tallrye.wlearn.controller.dto.response.UserSummary;
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
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return userService.getCurrentUser(currentUser);
    }

    @Transactional
    @GetMapping(value = "/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "email") String email) {
        return userService.checkUsernameAvailability(email);
    }

    @Transactional
    @GetMapping(value = "/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {

        return userService.getUserProfile(username);
    }

}
