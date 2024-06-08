package com.wwlei.authservice.repo;

import com.wwlei.authservice.repo.jpa.UserRepository;
import com.wwlei.authservice.repo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserAggregateRepo {
    private final UserRepository userRepository;
    public User findByUserId(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new IllegalArgumentException("user not find"));
    }

    public User createUser(User user) {
        user.encryptPassword();
        return userRepository.save(user);
    }

    public boolean isExists(User user) {
        return user.getId() != null && userRepository.existsById(user.getId());
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElseThrow(() -> new IllegalArgumentException("user not find"));
    }
}
