package com.soft.mikessolutions.userservice.controllers;

import com.soft.mikessolutions.userservice.assemblers.AddressResourceAssembler;
import com.soft.mikessolutions.userservice.assemblers.UserResourceAssembler;
import com.soft.mikessolutions.userservice.entities.Address;
import com.soft.mikessolutions.userservice.entities.User;
import com.soft.mikessolutions.userservice.services.AddressService;
import com.soft.mikessolutions.userservice.services.UserService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class AddressController {
    private final AddressService addressService;
    private final UserService userService;
    private final UserResourceAssembler userAssembler;
    private final AddressResourceAssembler addressAssembler;

    AddressController(AddressService addressService, UserService userService,
                      UserResourceAssembler userAssembler, AddressResourceAssembler addressAssembler) {
        this.addressService = addressService;
        this.userService = userService;
        this.userAssembler = userAssembler;
        this.addressAssembler = addressAssembler;
    }

    @GetMapping("/addresses")
    public Resources<Resource<Address>> all() {
        List<Resource<Address>> addresses = addressService.findAll().stream()
                .map(addressAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(addresses,
                linkTo(methodOn(AddressController.class).all()).withRel("addresses"));
    }

    @GetMapping("/addresses/{id:\\d+}")
    public Resource<Address> one(@PathVariable Long id) {
        Address address = addressService.findById(id);

        return addressAssembler.toResource(address);
    }

    @PostMapping("/addresses")
    public ResponseEntity<?> newAddress(@RequestBody Address newAddress) throws URISyntaxException {
        Resource<Address> resource = addressAssembler.toResource(addressService.save(newAddress));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/addresses/{id:\\d+}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        addressService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/addresses/{id:\\d+}")
    public ResponseEntity<?> replaceAddress(@RequestBody Address newAddress,
                                            @PathVariable Long id) {
        Address address = addressService.findById(id);
        address.setStreet(newAddress.getStreet());
        address.setStreetNumber(newAddress.getStreetNumber());
        address.setPostCode(newAddress.getPostCode());
        address.setCity(newAddress.getCity());
        address.setCountry(newAddress.getCountry());
        addressService.save(address);

        Resource<Address> resource = addressAssembler.toResource(address);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(resource);
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

        Resource<User> resource = userAssembler.toResource(user);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(resource);
    }
}
