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
import com.app.management.service.exceptions.InstanceNotFoundException;
import com.app.management.service.exceptions.InvalidParameterException;

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

    @SuppressWarnings("null")
    public void deleteGroup(String groupId) {
        groupRepository.deleteById(groupId);
    }

    @SuppressWarnings("null")
    public void removeUserFromGroup(String groupId) throws InstanceNotFoundException, InvalidParameterException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        
        Group group = groupRepository.findByIdAndMemberIdsContaining(groupId, userId)
            .orElseThrow(() -> new InstanceNotFoundException("Grupo no encontrado o no eres miembro"));
        
        List<String> memberIds = group.getMemberIds();
        
        if (memberIds.size() == 1) {
            groupRepository.deleteById(groupId);
            return;
        }

        if (group.getOwnerId().equals(userId)) {
            String newOwner = memberIds.stream()
                .filter(memberId -> !memberId.equals(userId))
                .findFirst()
                .orElseThrow(() -> new InvalidParameterException("No se pudo encontrar un nuevo propietario"));
            
            group.setOwnerId(newOwner);
        }

        memberIds.remove(userId);
        group.setMemberIds(memberIds);
        groupRepository.save(group);
    }
    
}
