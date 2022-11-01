package com.example.jwt.service;

import com.example.jwt.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUserName(String name);
    Boolean existsUserByUserName(String name);
    Boolean existsUserByEmail(String email);
    User save(User user);
}

