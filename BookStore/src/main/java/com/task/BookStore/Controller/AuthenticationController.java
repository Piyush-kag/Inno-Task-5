package com.task.BookStore.Controller;

import com.task.BookStore.Entity.Book;
import com.task.BookStore.Entity.User;
import com.task.BookStore.dto.SignUpRequest;
import com.task.BookStore.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest){
return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }
}
