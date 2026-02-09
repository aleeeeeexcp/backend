package com.app.management.mapper;

import com.app.management.bean.GroupDto;
import com.app.management.model.Group;

public class GroupMapper {

    public static GroupDto toGroupDto(Group group) {
        if (group == null) {
            return null;
        }
        return GroupDto.builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .ownerId(group.getOwnerId())
                .memberIds(group.getMemberIds())
                .createdAt(group.getCreatedAt())
                .build();
    }

    public static Group toGroup(GroupDto groupDto) {
        if (groupDto == null) {
            return null;
        }
        Group group = Group.builder()
                .id(groupDto.getId())
                .name(groupDto.getName())
                .description(groupDto.getDescription())
                .ownerId(groupDto.getOwnerId())
                .memberIds(groupDto.getMemberIds())
                .build();
        return group;
    }
    
}
