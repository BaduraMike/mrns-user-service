package com.soft.mikessolutions.userservice.web;

import com.soft.mikessolutions.userservice.services.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyWebLayerTest {
    private final String BASE_URL = "/companies";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyService companyService;

    @Test
    public void getAllCompaniesShouldReturnHttpStatus200Ok() throws Exception {
        this.mockMvc.perform(get(BASE_URL)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getCompanyByExistingIdShouldReturnHttpStatus200Ok() throws Exception {
        long id = companyService.findAll().size();
        String urlToExistingCompany = BASE_URL + "/" + id;

        this.mockMvc.perform(get(urlToExistingCompany)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getCompanyByNonExistingIdShouldReturnHttpStatus404NotFound() throws Exception {
        long id = companyService.findAll().size() + 1;
        String urlToNonExistingCompany = BASE_URL + "/" + id;

        this.mockMvc.perform(get(urlToNonExistingCompany)).andDo(print()).andExpect(status().isNotFound());
    }
}
