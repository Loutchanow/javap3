package com.chatop.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.service.JWTService;





@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private JWTService jwtService;

	public AuthController(JWTService jwtService) {
		this.jwtService = jwtService;
	}

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
    
    @PostMapping("/testjwt")
	public String getToken(Authentication authentication) {
		String token = jwtService.generateToken(authentication);
		return token;
	}
}

