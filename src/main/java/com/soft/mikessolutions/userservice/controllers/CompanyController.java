package com.soft.mikessolutions.userservice.controllers;

import com.soft.mikessolutions.userservice.assemblers.CompanyResourceAssembler;
import com.soft.mikessolutions.userservice.assemblers.UserResourceAssembler;
import com.soft.mikessolutions.userservice.entities.Company;
import com.soft.mikessolutions.userservice.entities.User;
import com.soft.mikessolutions.userservice.services.CompanyService;
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
public class CompanyController {
    private final CompanyService companyService;
    private final UserService userService;
    private final CompanyResourceAssembler companyAssembler;
    private final UserResourceAssembler userAssembler;

    CompanyController(CompanyService companyService, CompanyResourceAssembler companyResourceAssembler,
                      UserService userService, UserResourceAssembler userAssembler) {
        this.companyService = companyService;
        this.companyAssembler = companyResourceAssembler;
        this.userService = userService;
        this.userAssembler = userAssembler;
    }

    @GetMapping("/companies")
    public Resources<Resource<Company>> getAllCompanies() {
        List<Resource<Company>> companies = companyService.findAll().stream()
                .map(companyAssembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(companies,
                linkTo(methodOn(CompanyController.class).getAllCompanies()).withRel("companies"));
    }

    @GetMapping("/companies/{id:\\d+}")
    public Resource<Company> getCompanyById(@PathVariable Long id) {
        Company company = companyService.findById(id);

        return companyAssembler.toResource(company);
    }

    @PostMapping("/companies")
    public ResponseEntity<?> newAddress(@RequestBody Company newCompany) throws URISyntaxException {
        Resource<Company> resource = companyAssembler.toResource(companyService.save(newCompany));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/companies/{id:\\d+}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        companyService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/companies/{id:\\d+}")
    public ResponseEntity<?> replaceCompany(@RequestBody Company newCompany,
                                            @PathVariable Long id) {
        Company company = companyService.findById(id);
        company.setCompanyName(newCompany.getCompanyName());
        company.setVatIdNumber(newCompany.getVatIdNumber());
        company.setAddress(newCompany.getAddress());
        companyService.save(company);

        Resource<Company> resource = companyAssembler.toResource(company);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(resource);
    }

    @PutMapping("/users/{userId:\\d+}/companies/{companyId:\\d+}")
    public ResponseEntity<?> replaceUserCompany(@RequestBody Company newCompany, @PathVariable Long userId,
                                                @PathVariable Long companyId) {
        User user = userService.findById(userId);
        Company companyToUpdate = companyService.findById(companyId);

        companyToUpdate.setCompanyName(newCompany.getCompanyName());
        companyToUpdate.setVatIdNumber(newCompany.getVatIdNumber());
        companyToUpdate.setAddress(newCompany.getAddress());
        user.setCompany(companyToUpdate);
        userService.save(user);

        Resource<User> resource =userAssembler.toResource(user);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(resource);
    }
}
