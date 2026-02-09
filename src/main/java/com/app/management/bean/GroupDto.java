package com.app.management.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {
    
    private String id;
    private String name;
    private String description;
    private String ownerId;
    private List<String> memberIds;
    private String createdAt;
}
