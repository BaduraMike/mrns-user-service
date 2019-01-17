package com.soft.mikessolutions.userservice.services;

import com.soft.mikessolutions.userservice.entities.Address;

public interface AddressService extends CrudService<Address, Long> {

    Address findByAllParameters(String street, String streetNumber, String postCode, String city, String country);

}