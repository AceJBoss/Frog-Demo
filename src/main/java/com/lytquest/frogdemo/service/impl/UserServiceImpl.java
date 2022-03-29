package com.lytquest.frogdemo.service.impl;

import com.lytquest.frogdemo.entity.Role;
import com.lytquest.frogdemo.entity.User;
import com.lytquest.frogdemo.exception.ResourceNotFoundException;
import com.lytquest.frogdemo.repository.RoleRepository;
import com.lytquest.frogdemo.repository.UserRepository;
import com.lytquest.frogdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User createAdminAccount(User user) throws ResourceNotFoundException {
        return roleRepository.findById(1L).map(role->{
            user.setRole(role);
            return userRepository.save(user);
        }).orElseThrow(()-> new ResourceNotFoundException("Role Id does not exist"));
    }

}
