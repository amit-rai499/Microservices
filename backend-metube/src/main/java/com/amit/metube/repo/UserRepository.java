package com.amit.metube.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.amit.metube.model.User;

public interface UserRepository extends MongoRepository<User,String> {
	
	public Optional<User> findBySub(String sub);
	
}
