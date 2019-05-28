package com.tallrye.wlearn.controller;

import com.tallrye.wlearn.dto.LoginRequestDto;
import com.tallrye.wlearn.dto.SignUpRequestDto;
import com.tallrye.wlearn.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private AuthService authenticate;

    public AuthController(AuthService authenticate) {
        this.authenticate = authenticate;
    }

    @Transactional
    @PostMapping(value = "/signin")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authenticate.authenticateUser(loginRequestDto);
    }
    @Transactional
    @PostMapping(value = "/signup")
    public ResponseEntity registerUser(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        return authenticate.registerUser(signUpRequestDto);
    }
}
