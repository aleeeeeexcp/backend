package com.app.management.model.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.management.model.entities.Users;
import com.app.management.model.exceptions.DuplicateInstanceException;
import com.app.management.model.repositories.UsersRepository;
import com.app.management.model.services.exceptions.InvalidParameter;

@Service
public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(Users user) throws DuplicateInstanceException {

        if (usersRepository.existsByEmail(user.getEmail()) || usersRepository.existsByUsername(user.getUsername())) {
            throw new  DuplicateInstanceException("No repeat email or username");
        }
        
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        usersRepository.save(user);
    }

    @Override
    public Users login(String username, String password) throws InvalidParameter {
        Users user = usersRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidParameter("Invalid username or password");
        }
        return user;
    }

    


    
}
