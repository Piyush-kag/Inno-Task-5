package com.task.BookStore.dto;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
