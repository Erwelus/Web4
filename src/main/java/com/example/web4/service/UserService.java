package com.example.web4.service;


import com.example.web4.model.User;

public interface UserService {
    User register(User user);
    User findByUsername(String username);
    User getCurrentUser();
}
