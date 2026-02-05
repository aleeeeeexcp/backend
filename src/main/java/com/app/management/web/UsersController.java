package com.app.management.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.management.bean.AuthenticatedUsersDto;
import com.app.management.bean.LoginParamsDto;
import com.app.management.bean.UsersDto;
import com.app.management.config.JwtGenerator;
import com.app.management.config.JwtInfo;
import com.app.management.mapper.UsersMapper;
import com.app.management.model.Users;
import com.app.management.service.UsersService;
import com.app.management.service.exceptions.DuplicateInstanceException;
import com.app.management.service.exceptions.InvalidParameterException;

@RestController
@RequestMapping("/users")
public class UsersController {
    
    private UsersService usersService;

    private JwtGenerator jwtGenerator;

    public UsersController(UsersService usersService, JwtGenerator jwtGenerator) {
        this.usersService = usersService;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticatedUsersDto> singUp(@RequestBody UsersDto usersDto) throws DuplicateInstanceException {
        Users user = UsersMapper.toUsers(usersDto);
        usersService.signUp(user);
        AuthenticatedUsersDto authenticatedUsersDto = UsersMapper.toAuthenticatedUserDto(generateServiceToken(user), user);
        return ResponseEntity.ok(authenticatedUsersDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticatedUsersDto> login(@RequestBody LoginParamsDto loginParamsDto) throws InvalidParameterException {
        Users user = usersService.login(loginParamsDto.getUsername(), loginParamsDto.getPassword());
        AuthenticatedUsersDto authenticatedUsersDto = UsersMapper.toAuthenticatedUserDto(generateServiceToken(user), user);
        return ResponseEntity.ok(authenticatedUsersDto);
    }

    private String generateServiceToken(Users users){
        String roleName = users.getRoleType() != null ? users.getRoleType().name() : "USER";
        JwtInfo jwtInfo = new JwtInfo(users.getId(), users.getUsername(), roleName);
        return jwtGenerator.generate(jwtInfo);
    }

}
