package com.example.pfms.service;

import com.example.pfms.model.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    User registeredUser(User user);
    User findByEmail(String email);
    User findById(Long id);
    User save(User user);
}
