package com.myproject.backendminiproject.service;

import com.myproject.backendminiproject.dto.UserCreateRequest;
import com.myproject.backendminiproject.entities.User;
import com.myproject.backendminiproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public void createUser(UserCreateRequest userCreateRequest){

        Set<String> roleSet = Arrays.stream(userCreateRequest.getRole().split(","))
                .map(String::trim)
                .collect(Collectors.toSet());

        User user = User.builder()
                .username(userCreateRequest.getUsername())
                .password(encoder.encode(userCreateRequest.getPassword()))
                .roles(roleSet)
                .build();

        userRepository.save(user);
    }
}
