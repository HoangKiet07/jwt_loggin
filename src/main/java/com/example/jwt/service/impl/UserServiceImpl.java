package com.example.jwt.service.impl;

import com.example.jwt.model.User;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByUserName(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public Boolean existsUserByUserName(String name) {
        return userRepository.existsUserByUserName(name);
    }

    @Override
    public Boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
