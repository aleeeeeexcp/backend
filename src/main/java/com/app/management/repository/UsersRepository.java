package com.app.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.app.management.model.Users;

public interface UsersRepository extends MongoRepository<Users, String> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
    
    Users findByUsername(String username);

    Users findByEmail(String email);

}
