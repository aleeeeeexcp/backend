package com.app.management.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.app.management.constants.RoleType;
import com.app.management.model.Users;
import com.app.management.repository.UsersRepository;
import com.app.management.service.exceptions.DuplicateInstanceException;
import com.app.management.service.exceptions.InvalidParameterException;

@Service
public class UsersService {

    private UsersRepository usersRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signUp(Users user) throws DuplicateInstanceException {

        if (usersRepository.existsByEmail(user.getEmail()) || usersRepository.existsByUsername(user.getUsername())) {
            throw new  DuplicateInstanceException("No repeat email or username");
        }
        
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoleType(RoleType.USER);

        usersRepository.save(user);
    }

    public Users login(String username, String password) throws InvalidParameterException {
        Users user = usersRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidParameterException("Invalid username or password");
        }
        return user;
    } 

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public void deleteUsers(String id) {
        usersRepository.deleteById(id);
    }
    
}
