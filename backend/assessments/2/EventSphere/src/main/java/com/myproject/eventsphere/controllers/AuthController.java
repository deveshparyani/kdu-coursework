package com.myproject.eventsphere.controllers;

import com.myproject.eventsphere.dto.LoginRequest;
import com.myproject.eventsphere.entities.User;
import com.myproject.eventsphere.security.JwtUtil;
import com.myproject.eventsphere.utils.ConstantsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ConstantsUtils.API_BASE_URL + "/login")
public class AuthController {

    private static final Logger log =
            LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;


    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public String login(@RequestBody LoginRequest request) {

        System.out.println("CONTROLLER RECEIVED: " + request.getUsername() + " / " + request.getPassword());

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        log.info("User {} logged in successfully", userDetails.getUsername());

        return jwtUtil.generateToken(
                userDetails.getUsername(),
                userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
    }

    @PostMapping("/generate-hash")
    public String getHash(@RequestBody LoginRequest request) { // Changed String to LoginRequest
        return passwordEncoder.encode(request.getPassword());
    }

}