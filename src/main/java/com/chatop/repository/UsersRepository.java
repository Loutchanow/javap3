package com.chatop.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chatop.model.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {
	Optional<Users> findByEmail(String email);
}