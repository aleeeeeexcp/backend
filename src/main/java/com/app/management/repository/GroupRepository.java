package com.app.management.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.management.model.Group;

public interface GroupRepository extends MongoRepository<Group, String> {
    
    List<Group> findByMemberIdsContaining(String userId);
}
