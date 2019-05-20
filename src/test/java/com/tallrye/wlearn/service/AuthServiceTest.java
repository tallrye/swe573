package com.tallrye.wlearn.service;

import com.tallrye.wlearn.TestUtils;
import com.tallrye.wlearn.controller.dto.request.LoginRequest;
import com.tallrye.wlearn.controller.dto.request.SignUpRequest;
import com.tallrye.wlearn.controller.dto.response.ApiResponse;
import com.tallrye.wlearn.persistence.UserRepository;
import com.tallrye.wlearn.security.JwtTokenProvider;
import com.tallrye.wlearn.service.implementation.AuthServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private final AuthService sut = new AuthServiceImpl(authenticationManager, userRepository, passwordEncoder,
            tokenProvider);

    @Test
    public void testRegisterUser_UsernameExists(){
        //Prepare
        final SignUpRequest signUpRequest = TestUtils.createDummySignUpRequest();
        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(true);
        //Test
        final ResponseEntity<ApiResponse> responseEntity = sut.registerUser(signUpRequest);
        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }


    @Test
    public void testRegisterUser_MailExists(){
        //Prepare
        final SignUpRequest signUpRequest = TestUtils.createDummySignUpRequest();
        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);
        //Test
        final ResponseEntity<ApiResponse> responseEntity = sut.registerUser(signUpRequest);
        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected = IllegalStateException.class)
    public void testRegisterUser_Success(){
        //Prepare
        final SignUpRequest signUpRequest = TestUtils.createDummySignUpRequest();
        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(false);
        //Test
         sut.registerUser(signUpRequest);
    }


    @Test()
    public void testAuthenticateUser_Success(){
        //Prepare
        final LoginRequest loginRequest = TestUtils.createDummyLoginRequest();
        //Test
        final ResponseEntity responseEntity = sut.authenticateUser(loginRequest);
        //Verify
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

}
