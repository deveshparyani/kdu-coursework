package com.myproject.backendminiproject.controller;

import com.myproject.backendminiproject.dto.UserCreateRequest;
import com.myproject.backendminiproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.myproject.backendminiproject.constants.ApiConstants.BASE_URL;

@RestController
@RequestMapping(BASE_URL + "/register")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody UserCreateRequest userCreateRequest){
        userService.createUser(userCreateRequest);
    }
}
