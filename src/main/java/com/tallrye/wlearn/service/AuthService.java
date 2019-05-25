package com.tallrye.wlearn.service.implementation;

import com.tallrye.wlearn.dto.LoginRequestDto;
import com.tallrye.wlearn.dto.SignUpRequestDto;
import com.tallrye.wlearn.dto.ApiResponseDto;
import com.tallrye.wlearn.dto.JwtAuthenticationResponseDto;
import com.tallrye.wlearn.entity.UserEntity;
import com.tallrye.wlearn.persistence.UserRepository;
import com.tallrye.wlearn.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class AuthServiceImpl {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider tokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
            UserRepository userRepository, PasswordEncoder passwordEncoder,
            JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public ResponseEntity<ApiResponseDto> registerUser(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByUsername(signUpRequestDto.getUsername())) {
            return new ResponseEntity<>(new ApiResponseDto(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            return new ResponseEntity<>(new ApiResponseDto(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        final UserEntity userEntity = UserEntity.builder().name(signUpRequestDto.getName()).username(signUpRequestDto.getUsername())
                .email(signUpRequestDto.getEmail()).password(signUpRequestDto.getPassword()).build();

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        UserEntity result = userRepository.save(userEntity);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponseDto(true, "UserEntity registered successfully"));
    }

    public ResponseEntity authenticateUser(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsernameOrEmail(),
                        loginRequestDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponseDto(jwt));
    }
}
