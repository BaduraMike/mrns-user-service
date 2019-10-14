package com.soft.mikessolutions.userservice.assemblers;

import com.soft.mikessolutions.userservice.controllers.CompanyController;
import com.soft.mikessolutions.userservice.entities.Company;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CompanyResourceAssembler implements ResourceAssembler<Company, Resource<Company>> {

    @Override
    public Resource<Company> toResource(Company company) {
        return new Resource<>(company,
                linkTo(methodOn(CompanyController.class).getCompanyById(company.getIdentityNumber())).withSelfRel(),
                linkTo(methodOn(CompanyController.class).getAllCompanies()).withRel("companies"));
    }
}
