package com.soft.mikessolutions.userservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Override
    public Long getId() {
        return id;
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
}