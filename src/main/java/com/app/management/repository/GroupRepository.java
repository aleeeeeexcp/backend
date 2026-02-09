package com.app.management.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.management.model.Group;

public interface GroupRepository extends MongoRepository<Group, String> {
    
}
