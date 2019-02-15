package com.soft.mikessolutions.userservice.exceptions.address;

public class AddressAlreadyExistsException extends RuntimeException {
    public AddressAlreadyExistsException(Long id) {
        super("Address with the given parameters already exists with {id} = " + id);
    }
}
