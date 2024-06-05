package com.wwlei.authservice.controller;

import com.wwlei.authservice.repo.model.User;
import com.wwlei.authservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public final class UserController {
    private final UserService userService;

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable("id") Long id) {
        return userService.getUserAggregate(id);
    }

    @PutMapping("/user")
    public User createOrUpdateUser(@RequestBody User user) {
        return userService.createUserAggregate(user);
    }
}
