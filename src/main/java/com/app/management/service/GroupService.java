package com.app.management.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.management.model.Group;
import com.app.management.repository.GroupRepository;

@Service
public class GroupService {

    private GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Group createGroup(Group group) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        
        group.setOwnerId(userId);
        group.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        List<String> members = group.getMemberIds();
        if (members == null) {
            members = new ArrayList<>();
        }

        if (!members.contains(userId)) {
            members.add(userId);
        }
        group.setMemberIds(members);
        
        groupRepository.save(group);
        return group;
    }

    public List<Group> getUserGroups() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        return groupRepository.findByMemberIdsContaining(userId);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public void deleteGroup(String groupId) {
        groupRepository.deleteById(groupId);
    }   

    
}
