package com.task.BookStore.service.impl;

import com.task.BookStore.Entity.Role;
import com.task.BookStore.Entity.User;
import com.task.BookStore.Repository.UserRepository;
import com.task.BookStore.dto.JwtAuthenticationResponse;
import com.task.BookStore.dto.RefreshTokenRequest;
import com.task.BookStore.dto.SignInRequest;
import com.task.BookStore.dto.SignUpRequest;
import com.task.BookStore.service.AuthenticationService;
import com.task.BookStore.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    public User signup(SignUpRequest signUpRequest){
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public JwtAuthenticationResponse signin(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
    var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("Invalid Username and password"));
    var jwt = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefereshToken(new HashMap<>(), user);

    JwtAuthenticationResponse jwtAuthenticationResponse= new JwtAuthenticationResponse();
    jwtAuthenticationResponse.setToken(jwt);
    jwtAuthenticationResponse.setRefreshToken(refreshToken);
    return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail=jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        if(jwtService.isTokenValid(refreshTokenRequest.getToken()), user){


        }
    }
}
