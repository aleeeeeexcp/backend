package com.app.management.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor 
public class UsersDto {
    
    private String id;
    private String username;
    private String email;
    private String password;
    private String roleType;

}
