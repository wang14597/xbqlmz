package com.wwlei.authservice.controller;

import com.wwlei.authservice.repo.model.User;
import com.wwlei.authservice.service.UserService;
import com.wwlei.common.interfaces.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public final class UserController {
    private final UserService userService;

    @GetMapping("/user/{id}")
    public ApiResponse<?> findUserById(@PathVariable("id") Long id) {
        return ApiResponse.success(userService.getUserAggregate(id));
    }

    @PutMapping("/user")
    public ApiResponse<?> createOrUpdateUser(@RequestBody User user) {
        return ApiResponse.success(userService.createUserAggregate(user));
    }

    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody User user) throws NoSuchAlgorithmException {
        return ApiResponse.success(userService.login(user));
    }
}
