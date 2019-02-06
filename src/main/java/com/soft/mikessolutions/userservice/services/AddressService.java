package com.soft.mikessolutions.userservice.services;

import com.soft.mikessolutions.userservice.entities.Address;
import org.springframework.stereotype.Service;

@Service
public interface AddressService extends CrudService<Address, Long> {

    Address findByAllParameters(String street, String streetNumber, String postCode, String city, String country);

}