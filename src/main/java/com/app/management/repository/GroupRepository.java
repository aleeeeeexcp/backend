package com.app.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.app.management.model.Group;

public interface GroupRepository extends MongoRepository<Group, String> {
    
    List<Group> findByMemberIdsContaining(String userId);
    
    Optional<Group> findByIdAndMemberIdsContaining(String groupId, String userId);
}
