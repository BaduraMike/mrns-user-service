package com.soft.mikessolutions.userservice.exceptions.company;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(Long id) {
        super("Company with {id} = " + id + " not found.");
    }
    public CompanyNotFoundException(String vatIdNumber) {
        super("Company with {vatIdNumber} = " + vatIdNumber + " not found.");
    }
}