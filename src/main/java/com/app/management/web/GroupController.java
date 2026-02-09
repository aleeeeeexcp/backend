package com.app.management.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.management.bean.GroupDto;
import com.app.management.mapper.GroupMapper;
import com.app.management.service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/createGroup")
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto) {
        return ResponseEntity.ok(GroupMapper.toGroupDto(groupService.createGroup(GroupMapper.toGroup(groupDto))));        
    }
    
}
