package com.tallrye.wlearn.security;

import com.tallrye.wlearn.entity.UserEntity;
import com.tallrye.wlearn.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        final UserEntity userEntity = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "UserEntity not found with username or email : " + usernameOrEmail));

        return UserPrincipal.create(userEntity);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        final UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("UserEntity not found with id : " + id));

        return UserPrincipal.create(userEntity);
    }

}