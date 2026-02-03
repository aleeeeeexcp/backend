package com.app.management.mapper;

import com.app.management.bean.UsersDto;
import com.app.management.model.Users;
import com.app.management.bean.AuthenticatedUsersDto;

public class UserDtoMapper {
    
    private UserDtoMapper() {
    }

    public static final UsersDto toUsersDto(Users user) {
        return new UsersDto(user.getId(), user.getUsername(), user.getEmail(), user.getRoleType().toString());
    }

    public static final Users toUsers(UsersDto usersDto) {
        return new Users(usersDto.getId(), usersDto.getUsername(), usersDto.getEmail(), usersDto.getPassword());
    }

    public final static AuthenticatedUsersDto toAuthenticatedUserDto(String serviceToken, Users user) {
		
		return new AuthenticatedUsersDto(serviceToken, toUsersDto(user));
		
	}
}
