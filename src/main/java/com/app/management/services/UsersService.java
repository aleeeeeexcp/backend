package com.app.management.services;

import com.app.management.model.Users;
import com.app.management.services.exceptions.DuplicateInstanceException;
import com.app.management.services.exceptions.InstanceNotFoundException;
import com.app.management.services.exceptions.InvalidParameterException;

public interface UsersService {
    
    void signUp(Users user) throws DuplicateInstanceException;

    Users login(String username, String password) throws InvalidParameterException;

    Users loginFromId(String id) throws InstanceNotFoundException;
    
}
