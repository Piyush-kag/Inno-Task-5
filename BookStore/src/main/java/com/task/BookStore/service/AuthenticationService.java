package com.task.BookStore.service;

import com.task.BookStore.Entity.User;
import com.task.BookStore.dto.SignUpRequest;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);
}
