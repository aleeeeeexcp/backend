package com.app.management.service;

import com.app.management.model.Group;
import com.app.management.repository.GroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    private Group createGroup(String id, String name, String description, String ownerId, List<String> memberIds, String createdAt) {
        return Group.builder()
                .id(id)
                .name(name)
                .description(description)
                .ownerId(ownerId)
                .memberIds(memberIds)
                .createdAt(createdAt)
                .build();
    }

    @Test
    void getAllGroups_returnsAllGroups() {
        List<Group> groups = List.of(
                createGroup("g1", "Family", "Family group", "user1", List.of("user1"), "2026-02-11T10:00:00"),
                createGroup("g2", "Work", "Work group", "user2", List.of("user2"), "2026-02-11T11:00:00"),
                createGroup("g3", "Friends", "Friends group", "user3", List.of("user3"), "2026-02-11T12:00:00")
        );
        when(groupRepository.findAll()).thenReturn(groups);

        List<Group> result = groupService.getAllGroups();

        assertEquals(3, result.size());
        assertEquals("g1", result.get(0).getId());
        assertEquals("g2", result.get(1).getId());
        assertEquals("g3", result.get(2).getId());
    }

    @Test
    void deleteGroup_deletesGroupById() {
        doNothing().when(groupRepository).deleteById("g1");

        groupService.deleteGroup("g1");

        verify(groupRepository).deleteById("g1");
    }

    @Test
    void findByMemberIdsContaining_returnsGroups() {
        List<Group> groups = List.of(
                createGroup("g1", "Family", "Family group", "user1", List.of("user1", "user2"), "2026-02-11T10:00:00")
        );
        when(groupRepository.findByMemberIdsContaining("user1")).thenReturn(groups);

        List<Group> result = groupRepository.findByMemberIdsContaining("user1");

        assertEquals(1, result.size());
        assertEquals("g1", result.get(0).getId());
    }
}
