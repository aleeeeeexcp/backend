package com.app.management.service;

import com.app.management.model.Users;
import com.app.management.service.exceptions.DuplicateInstanceException;
import com.app.management.service.exceptions.InstanceNotFoundException;
import com.app.management.service.exceptions.InvalidParameterException;

public interface UsersService {
    
    void signUp(Users user) throws DuplicateInstanceException;

    Users login(String username, String password) throws InvalidParameterException;

    Users loginFromId(String id) throws InstanceNotFoundException;
    
}
