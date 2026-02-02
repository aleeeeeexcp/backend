package com.app.management.model.services;

import com.app.management.model.entities.Users;
import com.app.management.model.exceptions.DuplicateInstanceException;
import com.app.management.model.exceptions.InstanceNotFoundException;
import com.app.management.model.services.exceptions.InvalidParameter;

public interface UsersService {
    
    void signUp(Users user) throws DuplicateInstanceException;

    Users login(String username, String password) throws InvalidParameter;

    Users loginFromId(String id) throws InstanceNotFoundException;
    

}
