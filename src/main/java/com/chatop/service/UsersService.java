package com.chatop.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.dto.UsersDTO;
import com.chatop.model.Users;
import com.chatop.repository.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository userRepository;
	
	public UsersDTO save(UsersDTO usersDTO) {
		Users user = new Users();

		user.setName(usersDTO.getName());
		user.setEmail(usersDTO.getEmail());
		user.setPassword(usersDTO.getPassword());
		user.setCreated_at(LocalDateTime.now());
		user.setUpdated_at(LocalDateTime.now());

		Users savedUser = userRepository.save(user);

		UsersDTO response = new UsersDTO();
		response.setId(savedUser.getId());
		response.setName(savedUser.getName());
		response.setEmail(savedUser.getEmail());
		response.setCreated_at(savedUser.getCreated_at());
		response.setUpdated_at(savedUser.getUpdated_at());

		return response;
	}
}
