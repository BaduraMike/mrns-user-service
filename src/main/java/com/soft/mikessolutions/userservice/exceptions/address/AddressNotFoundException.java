package com.soft.mikessolutions.userservice.exceptions.address;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Long id) {
        super("Address with {id} = " + id + " not found.");
    }
}
