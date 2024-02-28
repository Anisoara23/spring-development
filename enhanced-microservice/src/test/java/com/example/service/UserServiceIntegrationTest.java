package com.example.service;

import com.example.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void testSignup() {
        Optional<User> signuped = userService.signup("john", "letmein", "john", "smith");
        assertNotEquals(signuped.get().getPassword(), "letmein");
    }
}