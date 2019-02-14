package com.soft.mikessolutions.userservice.controllers;

import com.soft.mikessolutions.userservice.assemblers.UserResourceAssembler;
import com.soft.mikessolutions.userservice.entities.Address;
import com.soft.mikessolutions.userservice.entities.User;
import com.soft.mikessolutions.userservice.services.AddressService;
import com.soft.mikessolutions.userservice.services.UserService;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {
    private final AddressService addressService;
    private final UserService userService;
    private final UserResourceAssembler assembler;

    AddressController(AddressService addressService, UserService userService, UserResourceAssembler assembler) {
        this.addressService = addressService;
        this.userService = userService;
        this.assembler = assembler;
    }

    @PutMapping("/users/{userId:\\d+}/addresses/{addressId:\\d+}")
    public ResponseEntity<?> replaceUserAddress(@RequestBody Address newAddress, @PathVariable Long userId,
                                                @PathVariable Long addressId) {
        addressService.checkExistence(newAddress.getStreet(), newAddress.getStreetNumber(), newAddress.getPostCode(),
                newAddress.getCity(), newAddress.getCountry());
        User user = userService.findById(userId);
        Address addressToUpdate = addressService.findById(addressId);

        addressToUpdate.setStreet(newAddress.getStreet());
        addressToUpdate.setStreetNumber(newAddress.getStreetNumber());
        addressToUpdate.setPostCode(newAddress.getPostCode());
        addressToUpdate.setCity(newAddress.getCity());
        addressToUpdate.setCountry(newAddress.getCountry());
        user.setAddress(addressToUpdate);
        userService.save(user);

        Resource<User> resource = assembler.toResource(user);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(resource);
    }
}
