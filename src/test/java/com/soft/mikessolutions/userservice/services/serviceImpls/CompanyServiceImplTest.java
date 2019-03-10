package com.soft.mikessolutions.userservice.services.serviceImpls;

import com.soft.mikessolutions.userservice.entities.Company;
import com.soft.mikessolutions.userservice.exceptions.company.CompanyNotFoundException;
import com.soft.mikessolutions.userservice.services.CompanyService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyServiceImplTest {

    @Autowired
    private CompanyService companyService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldFindAllCompanies() {
        Assert.assertFalse(companyService.findAll().isEmpty());
    }

    @Test
    public void shouldFindCompanyByExistingId() {
        long id = companyService.findAll().size();

        Assert.assertNotNull(companyService.findById(id));
    }

    @Test
    public void shouldThrowCompanyNotFoundExceptionForNonExistingId() {
        //GIVEN
        //WHEN
        Long id = (long) (companyService.findAll().size() + 1);
        //THEN
        expectedException.expect(CompanyNotFoundException.class);
        expectedException.expectMessage("Company with {id} = " + id + " not found.");
        companyService.findById(id);
    }

    @Test
    public void shouldFindCompanyByVatIdNumber() {
        long id = companyService.findAll().size() - 1;
        Company company1 = companyService.findById(id);
        Company company2 = companyService.findByVatIdNumber(company1.getVatIdNumber());

        Assert.assertNotNull(company2);
    }

    @Test
    public void shouldThrowCompanyNotFoundExceptionForNonExistingVatIdNumber() {
        //GIVEN
        //WHEN
        String nonExistingVatIdNumber = "qwerty12345";
        //THEN
        expectedException.expect(CompanyNotFoundException.class);
        expectedException.expectMessage("Company with {vatIdNumber} = " + nonExistingVatIdNumber + " not found.");
        companyService.findByVatIdNumber(nonExistingVatIdNumber);
    }

    @Test
    public void shouldSaveNewCompany() {
        //GIVEN
        int preSaveCompanyCount = companyService.findAll().size();
        Company newCompany = new Company();
        //WHEN
        companyService.save(newCompany);
        int postSaveCompanyCount = companyService.findAll().size();
        //THEN
        Assert.assertEquals(1, postSaveCompanyCount - preSaveCompanyCount);
    }


    @Test
    public void shouldDeleteCompany() {
        //GIVEN
        Company newUser = new Company();
        companyService.save(newUser);
        int preDeleteCompanyCount = companyService.findAll().size();
        //WHEN
        companyService.delete(newUser);
        int postDeleteCompanyCount = companyService.findAll().size();
        //THEN
        Assert.assertEquals(1, preDeleteCompanyCount - postDeleteCompanyCount);
    }

    @Test
    public void shouldDeleteCompanyByExistingId() {
        //GIVEN
        Company newCompany = new Company();
        companyService.save(newCompany);
        int preDeleteCompanyCount = companyService.findAll().size();
        //WHEN
        companyService.deleteById(newCompany.getId());
        int postDeleteCompanyCount = companyService.findAll().size();
        //THEN
        Assert.assertEquals(1, preDeleteCompanyCount - postDeleteCompanyCount);
    }
}
