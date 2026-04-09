package com.civicconnect.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.civicconnect.model.User;
import com.civicconnect.security.JwtUtil;
import com.civicconnect.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {

        User existing = service.findByEmail(user.getEmail());

        if (!existing.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(existing.getEmail());

        Map<String, String> res = new HashMap<>();
        res.put("token", token);
        res.put("role", existing.getRole());
        res.put("userId", existing.getId().toString());

        return res;
    }
}