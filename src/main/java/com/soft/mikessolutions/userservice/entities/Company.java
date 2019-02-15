package com.soft.mikessolutions.userservice.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Company extends BaseEntity {

    private String companyName;
    private String vatIdNumber;
    @ManyToOne
    private Address address;

    public Company() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public String vatIdNumber() {
        return vatIdNumber;
    }

    public Address getAddress() {
        return address;
    }
}