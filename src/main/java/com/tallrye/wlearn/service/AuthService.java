package com.tallrye.wlearn.service;

import com.tallrye.wlearn.controller.dto.request.LoginRequest;
import com.tallrye.wlearn.controller.dto.request.SignUpRequest;
import com.tallrye.wlearn.controller.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<ApiResponse> registerUser(SignUpRequest signUpRequest);

    ResponseEntity authenticateUser(LoginRequest loginRequest);

}
