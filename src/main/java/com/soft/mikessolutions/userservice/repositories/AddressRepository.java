package com.soft.mikessolutions.userservice.repositories;

import com.soft.mikessolutions.userservice.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByStreetAndStreetNumberAndPostCodeAndCityAndCountry
            (String street, String streetNumber, String postCode, String city, String country);
}