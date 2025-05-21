package com.chatop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.dto.UsersDTO;
import com.chatop.model.Users;
import com.chatop.repository.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository userRepository;
	
	public UsersDTO save(UsersDTO usersDTO)
	{
		Users users = new Users();
    	users.setName(usersDTO.getName());
    	users.setPassword(usersDTO.getPassword());
    	users.setEmail(usersDTO.getEmail());
		userRepository.save(users);
		return usersDTO;
	}
}
