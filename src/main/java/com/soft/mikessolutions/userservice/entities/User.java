package com.soft.mikessolutions.userservice.entities;

import com.soft.mikessolutions.userservice.entities.enums.UserType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    @ManyToOne
    private Address address;
    @ManyToOne
    private Company company;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public Company getCompany() {
        return company;
    }

    public UserType getUserType() {
        return userType;
    }
}