package com.app.management.model.repositories;

import com.app.management.model.entities.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<Users , Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
    
    Users findByUsername(String username);

    Users findByEmail(String email);
}
