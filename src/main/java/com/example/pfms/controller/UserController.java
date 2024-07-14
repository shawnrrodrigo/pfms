package com.example.pfms.controller;

import com.example.pfms.model.User;
import com.example.pfms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registeredUser(@RequestBody User user){
        return userService.registeredUser(user);
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user){
        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null && user.getPassword().equals(existingUser.getPassword())) {
            return existingUser;
        }
        return null;
    }

    @PutMapping("/admin/activate/{id}")
    public User activateUser(@PathVariable Long id) {
        User user = userService.findById(id);
        user.setActive(true);
        return userService.save(user);
    }

    @PutMapping("/admin/deactivate/{id}")
    public User deactivateUser(@PathVariable Long id) {
        User user = userService.findById(id);
        user.setActive(false);
        return userService.save(user);
    }
}
