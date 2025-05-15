package com.chatop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.UsersDTO;
import com.chatop.model.Users;
import com.chatop.service.JWTService;
import com.chatop.service.UsersService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;





@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private JWTService jwtService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public AuthController(JWTService jwtService) {
		this.jwtService = jwtService;
	}

    @PostMapping("/register")
    public UsersDTO register(@RequestBody UsersDTO usersDTO) {
    	usersDTO.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
    	return usersService.save(usersDTO);
    }

    @PostMapping("/login")
    public String login(Authentication authentication) {
    	String token = jwtService.generateToken(authentication);
		return token;
    }

    @GetMapping("/me")
    public String me() {
        return "Me endpoint";
    }
}

