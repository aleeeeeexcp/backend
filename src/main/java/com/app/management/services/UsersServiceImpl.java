package com.app.management.services;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.management.model.Users;
import com.app.management.repositories.UsersRepository;
import com.app.management.services.exceptions.DuplicateInstanceException;
import com.app.management.services.exceptions.InstanceNotFoundException;
import com.app.management.services.exceptions.InvalidParameterException;

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
    public Users login(String username, String password) throws InvalidParameterException {
        Users user = usersRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidParameterException("Invalid username or password");
        }
        return user;
    }

    @Override
    public Users loginFromId(String id) throws InstanceNotFoundException {
        return checkUser(id);
    }

    private Users checkUser(String id) throws InstanceNotFoundException {
        Optional<Users> user = usersRepository.findById(id);
        if (!user.isPresent()) {
            throw new InstanceNotFoundException("User not found");
        }
        return user.get();
    }
    
}
