package com.soft.mikessolutions.userservice.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.soft.mikessolutions.userservice.controllers.UserController;
import com.soft.mikessolutions.userservice.entities.User;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserResourceAssembler implements ResourceAssembler<User, Resource<User>> {

    @Override
    public Resource<User> toResource(User user) {
        return new Resource<>(user,
                linkTo(methodOn(UserController.class).getUserById(user.getIdentityNumber())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
    }
}
