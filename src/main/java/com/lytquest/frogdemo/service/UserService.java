package com.lytquest.frogdemo.service;

import com.lytquest.frogdemo.entity.User;
import com.lytquest.frogdemo.exception.ResourceNotFoundException;

public interface UserService {
    public User createAdminAccount(User user, Long roleId) throws ResourceNotFoundException;
}
