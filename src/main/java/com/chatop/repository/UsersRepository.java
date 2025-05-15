package com.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chatop.model.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

}