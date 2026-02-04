package com.app.management.mapper;

import com.app.management.bean.UsersDto;
import com.app.management.model.Users;
import com.app.management.bean.AuthenticatedUsersDto;

public class UserDtoMapper {
    
    private UserDtoMapper() {
    }

    public static final UsersDto toUsersDto(Users user) {
        return UsersDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roleType(user.getRoleType() != null ? user.getRoleType().toString() : null)
                .build();
    }

    public static final Users toUsers(UsersDto usersDto) {
        return Users.builder()
                .id(usersDto.getId())
                .username(usersDto.getUsername())
                .email(usersDto.getEmail())
                .password(usersDto.getPassword())
                .build();
    }

    public final static AuthenticatedUsersDto toAuthenticatedUserDto(String serviceToken, Users user) {
		
		return new AuthenticatedUsersDto(serviceToken, toUsersDto(user));
		
	}
}
