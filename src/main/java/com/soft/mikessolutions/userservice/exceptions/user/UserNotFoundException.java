package com.soft.mikessolutions.userservice.exceptions.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with {id} = " + id + " not found.");
    }

    public UserNotFoundException(String email) {
        super("User with {email} = " + email + " not found");
    }
}