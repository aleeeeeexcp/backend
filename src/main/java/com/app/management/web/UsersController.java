package com.app.management.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.management.bean.UsersDto;
import com.app.management.mapper.UserDtoConversor;
import com.app.management.model.Users;
import com.app.management.services.UsersService;
import com.app.management.services.exceptions.DuplicateInstanceException;

@RestController
@RequestMapping("/users")
public class UsersController {
    
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Users> singUp(@RequestBody UsersDto usersDto) throws DuplicateInstanceException {
        usersService.signUp(UserDtoConversor.toUsers(usersDto));
        return ResponseEntity.ok().build();
    }
}
