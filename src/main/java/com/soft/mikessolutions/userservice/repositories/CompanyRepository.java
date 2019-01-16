package com.soft.mikessolutions.userservice.repositories;

import com.soft.mikessolutions.userservice.entities.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
}