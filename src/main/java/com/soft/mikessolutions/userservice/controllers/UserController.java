package com.soft.mikessolutions.userservice.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.soft.mikessolutions.userservice.assemblers.UserResourceAssembler;
import com.soft.mikessolutions.userservice.entities.User;
import com.soft.mikessolutions.userservice.services.UserService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
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
    public Resource<User> one(@PathVariable Long id) {
        User user = userService.findById(id);

        return assembler.toResource(user);
    }

    @PostMapping("/users")
    public ResponseEntity<?> newUser(@RequestBody User newUser) throws URISyntaxException {
        Resource<User> resource = assembler.toResource(userService.save(newUser));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }
}
