package com.app.management.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.management.bean.LoginParamsDto;
import com.app.management.bean.UsersDto;
import com.app.management.mapper.UserDtoConversor;

import com.app.management.service.UsersService;
import com.app.management.service.exceptions.DuplicateInstanceException;
import com.app.management.service.exceptions.InvalidParameterException;

@RestController
@RequestMapping("/users")
public class UsersController {
    
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UsersDto> singUp(@RequestBody UsersDto usersDto) throws DuplicateInstanceException {
        usersService.signUp(UserDtoConversor.toUsers(usersDto));
        return ResponseEntity.ok(usersDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UsersDto> login(@RequestBody LoginParamsDto loginParamsDto) throws InvalidParameterException {
        UsersDto user = UserDtoConversor.toUsersDto(usersService.login(loginParamsDto.getUsername(), loginParamsDto.getPassword()));
        return ResponseEntity.ok(user);
    }

}
