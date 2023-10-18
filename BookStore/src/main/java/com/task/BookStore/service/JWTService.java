package com.task.BookStore.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface JWTService {
    public String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
    59:36 time of video
    String generateRefereshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
