package com.example.web;

import com.example.domain.User;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("signin")
    public Authentication login(@RequestBody @Valid LoginDto loginDto) {
        return userService.signin(loginDto.getUsername(), loginDto.getPassword());
    }

    @PostMapping("signup")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User signup(@RequestBody @Valid LoginDto loginDto) {
        return userService.signup(loginDto.getUsername(),
                loginDto.getPassword(),
                loginDto.getFirstName(),
                loginDto.getLastName())
                .orElseThrow(() -> new RuntimeException("User already exists"));
    }
}
