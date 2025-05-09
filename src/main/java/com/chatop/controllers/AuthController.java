package com.chatop.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/register")
    public String register() {
        return "Register endpoint";
    }

    @PostMapping("/login")
    public String login() {
        return "Login endpoint";
    }

    @GetMapping("/me")
    public String me() {
        return "Me endpoint";
    }
}

