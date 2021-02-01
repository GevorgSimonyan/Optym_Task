package com.gevorg.task.adminmax.repositories;

import com.gevorg.task.adminmax.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface UserRepository extends MongoRepository<User, String> {

	User findByEmail(String email);

	User findByToken(String token);

	Optional<User> findById(String id);

}
