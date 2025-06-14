package com.chatop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.LoginRequestDTO;
import com.chatop.dto.RegisterDTO;
import com.chatop.dto.TokenResponseDTO;
import com.chatop.dto.UsersDTO;
import com.chatop.model.Users;
import com.chatop.repository.UsersRepository;
import com.chatop.service.JWTService;
import com.chatop.service.UsersService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private JWTService jwtService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public AuthController(JWTService jwtService) {
		this.jwtService = jwtService;
	}
	@PostMapping("/register")
	public TokenResponseDTO register(@RequestBody RegisterDTO registerDTO) {
	    String rawPassword = registerDTO.getPassword();
	    String encoded = passwordEncoder.encode(rawPassword);

	    UsersDTO user = new UsersDTO();
	    user.setEmail(registerDTO.getEmail());
	    user.setName(registerDTO.getName());
	    user.setPassword(encoded);

	    usersService.save(user);

	    Authentication authentication = authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(user.getEmail(), rawPassword)
	    );
	    String token = jwtService.generateToken(authentication);
	    return new TokenResponseDTO(token);
	}

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody LoginRequestDTO request) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email, request.password)
        );
        String token = jwtService.generateToken(auth);
        return new TokenResponseDTO(token);
    }

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    public UsersDTO me(Authentication authentication) {
        String email = authentication.getName(); 
        Users user = usersRepository.findByEmail(email).orElseThrow();
        
        UsersDTO dto = new UsersDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCreated_at(user.getCreated_at());
        dto.setUpdated_at(user.getUpdated_at());
        
        return dto;
    }
}

