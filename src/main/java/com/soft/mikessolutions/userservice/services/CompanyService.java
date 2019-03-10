package com.soft.mikessolutions.userservice.services;

import com.soft.mikessolutions.userservice.entities.Company;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService extends CrudService<Company, Long> {

    Company findByVatIdNumber(String vatIdNumber);
}