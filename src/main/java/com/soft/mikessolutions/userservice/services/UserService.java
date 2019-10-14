package com.soft.mikessolutions.userservice.services;

import com.soft.mikessolutions.userservice.entities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends CrudService<User, Long> {
    User findByEmail(String email);
}