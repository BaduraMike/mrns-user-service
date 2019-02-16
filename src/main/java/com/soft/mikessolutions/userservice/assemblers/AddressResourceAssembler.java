package com.soft.mikessolutions.userservice.assemblers;

import com.soft.mikessolutions.userservice.controllers.AddressController;
import com.soft.mikessolutions.userservice.entities.Address;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class AddressResourceAssembler implements ResourceAssembler<Address, Resource<Address>> {

    @Override
    public Resource<Address> toResource(Address address) {
        return new Resource<>(address,
                linkTo(methodOn(AddressController.class).one(address.getId())).withSelfRel(),
                linkTo(methodOn(AddressController.class).all()).withRel("addresses"));
    }
}
