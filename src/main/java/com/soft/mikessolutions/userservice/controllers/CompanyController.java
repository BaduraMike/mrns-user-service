package com.soft.mikessolutions.userservice.controllers;

import com.soft.mikessolutions.userservice.assemblers.CompanyResourceAssembler;
import com.soft.mikessolutions.userservice.entities.Company;
import com.soft.mikessolutions.userservice.services.CompanyService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyResourceAssembler companyResourceAssembler;

    CompanyController(CompanyService companyService, CompanyResourceAssembler companyResourceAssembler) {
        this.companyService = companyService;
        this.companyResourceAssembler = companyResourceAssembler;
    }

    @GetMapping("/companies")
    public Resources<Resource<Company>> all() {
        List<Resource<Company>> companies = companyService.findAll().stream()
                .map(companyResourceAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(companies,
                linkTo(methodOn(CompanyController.class).all()).withRel("companies"));
    }
}
