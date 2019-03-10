package com.soft.mikessolutions.userservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Address extends BaseEntity {

    private String street;
    private String streetNumber;
    private String postCode;
    private String city;
    private String country;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST,
            mappedBy = "address")
    private Set<User> users;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST,
            mappedBy = "address")
    private Set<Company> companies;

    public Address() {
    }

    public Address(String street, String streetNumber, String postCode, String city, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}