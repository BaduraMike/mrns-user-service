package com.soft.mikessolutions.userservice.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.soft.mikessolutions.userservice.assemblers.UserResourceAssembler;
import com.soft.mikessolutions.userservice.entities.User;
import com.soft.mikessolutions.userservice.services.UserService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final UserService userService;

    private final UserResourceAssembler assembler;

    UserController(UserService userService,
                   UserResourceAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    @GetMapping("/users")
    public Resources<Resource<User>> all() {
        List<Resource<User>> users = userService.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(users,
                linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Resource<User> one(@PathVariable Long id) {
        User user = userService.findById(id);

        return assembler.toResource(user);
    }
}
