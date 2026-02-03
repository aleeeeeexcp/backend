package com.app.management.mapper;

import com.app.management.bean.UsersDto;
import com.app.management.model.Users;

public class UserDtoConversor {
    
    private UserDtoConversor() {
    }

    public static final UsersDto toUsersDto(Users user) {
        return new UsersDto(user.getId(), user.getUsername(), user.getEmail());
    }

    public static final Users toUsers(UsersDto usersDto) {
        return new Users(usersDto.getId(), usersDto.getUsername(), usersDto.getEmail(), usersDto.getPassword());
    }
}
