package com.example.pfms.controller;

import com.example.pfms.exception.custom.OperationNotAllowedException;
import com.example.pfms.model.User;
import com.example.pfms.response.UserResponseDTO;
import com.example.pfms.security.JwtTokenProvider;
import com.example.pfms.security.UserDetailsImpl;
import com.example.pfms.service.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public User registeredUser(@RequestBody User user){
        return userService.registeredUser(user);
    }

    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.createToken(user.getEmail());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            User userData = userService.findByEmail(user.getEmail());
            BeanUtils.copyProperties(userData, userResponseDTO);
            response.put("user", userResponseDTO);
            return response;
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email or password");
        }
    }

    @PutMapping("/admin/activate/{id}")
    public String activateUser(@PathVariable Long id, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        User loggedUser = userService.findById(userId);
        if(!loggedUser.getRole().equalsIgnoreCase("admin")){
            throw new OperationNotAllowedException("You don't have permission to activate user", HttpStatus.FORBIDDEN.value());
        }
        User user = userService.findById(id);
        user.setActive(true);
        userService.save(user);
        return "User activated Succesfully";
    }

    @PutMapping("/admin/deactivate/{id}")
    public String deactivateUser(@PathVariable Long id, Authentication authentication) {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        User loggedUser = userService.findById(userId);
        if(!loggedUser.getRole().equalsIgnoreCase("admin")){
            throw new OperationNotAllowedException("You don't have permission to activate user", HttpStatus.FORBIDDEN.value());
        }
        User user = userService.findById(id);
        user.setActive(false);
        userService.save(user);
        return "User deactivated successfully";
    }
}
