package com.soft.mikessolutions.userservice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.soft.mikessolutions.userservice.entities.Company;
import com.soft.mikessolutions.userservice.entities.User;
import com.soft.mikessolutions.userservice.services.CompanyService;
import com.soft.mikessolutions.userservice.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CompanyWebLayerTest {
    private final String COMPANY_BASE_URL = "/companies";
    private final String USER_BASE_URL = "/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @Test
    public void getAllCompaniesShouldReturnHttpStatus200Ok() throws Exception {
        this.mockMvc.perform(get(COMPANY_BASE_URL)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getCompanyByExistingIdShouldReturnHttpStatus200Ok() throws Exception {
        long id = companyService.findAll().size();
        String urlToExistingCompany = COMPANY_BASE_URL + "/" + id;

        this.mockMvc.perform(get(urlToExistingCompany)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getCompanyByNonExistingIdShouldReturnHttpStatus404NotFound() throws Exception {
        long id = companyService.findAll().size() + 1;
        String urlToNonExistingCompany = COMPANY_BASE_URL + "/" + id;

        this.mockMvc.perform(get(urlToNonExistingCompany)).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void postCompanyShouldReturnHttpStatus201Created() throws Exception {
        Company company = new Company();
        company.setCompanyName("ABCDEFG");
        company.setVatIdNumber("666-666-666");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(company);

        this.mockMvc.perform(post(COMPANY_BASE_URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void putCompanyByExistingIdShouldUpdateCompanyAndReturnHttpStatus204NoContent() throws Exception {
        long id = companyService.findAll().size();
        String existingCompanyPutUrl = COMPANY_BASE_URL + "/" + id;
        MockHttpServletRequestBuilder builder = createJsonRequest(existingCompanyPutUrl, "ABCDFGHIJK",
                "123-123-123");

        this.mockMvc.perform(builder)
                .andExpect(status()
                        .isNoContent())
                .andDo(print());
    }

    @Test
    public void putCompanyByNonExistingIdShouldReturnHttpStatus404NotFound() throws Exception {
        long id = companyService.findAll().size() + 1;
        String nonExistingCompanyPutUrl = COMPANY_BASE_URL + "/" + id;
        MockHttpServletRequestBuilder builder = createJsonRequest(nonExistingCompanyPutUrl, "ABCDFGHIJK",
                "123-123-123");

        this.mockMvc.perform(builder)
                .andExpect(status()
                        .isNotFound())
                .andDo(print());
    }

    @Test
    public void deleteCompanyByExistingIdShouldReturnHttpStatus204NoContent() throws Exception {
        Company company = new Company();
        companyService.save(company);
        long id = companyService.findAll().size();
        String urlToExistingCompany = COMPANY_BASE_URL + "/" + id;

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(urlToExistingCompany)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteCompanyByNonExistingIdShouldReturnHttpStatus404NotFound() throws Exception {
        long id = companyService.findAll().size() + 1;
        String urlToNonExistingCompany = COMPANY_BASE_URL + "/" + id;

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(urlToNonExistingCompany)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteChildCompanyByExistingIdShouldReturnHttpStatus400BadRequestForDataIntegrationViolation()
            throws Exception {
        User user = new User();
        Company company = new Company();
        companyService.save(company);
        user.setCompany(company);
        userService.save(user);

        long id = companyService.findAll().size();
        String urlToExistingChildCompany = COMPANY_BASE_URL + "/" + id;

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(urlToExistingChildCompany)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void putShouldUpdateCompanyByExistingIdForUserByExistingIdAndReturnHttpStatus204NoContent() throws Exception {
        String URL = USER_BASE_URL + "/" + userService.findAll().size() + "/"
                + COMPANY_BASE_URL + "/" + companyService.findAll().size();
        long existingCompanyId = companyService.findAll().size();
        Company existingCompany = companyService.findById(existingCompanyId);
        String randomVatIdNumber = String.valueOf(Math.random() * 12345);

        MockHttpServletRequestBuilder builder = createJsonRequest(URL, existingCompany.getCompanyName(),
                randomVatIdNumber);

        this.mockMvc.perform(builder)
                .andExpect(status()
                        .isNoContent())
                .andDo(print());
    }

    private MockHttpServletRequestBuilder createJsonRequest(String URL, String companyName, String vatIdNumber)
            throws JsonProcessingException {
        Company company = new Company();
        company.setCompanyName(companyName);
        company.setVatIdNumber(vatIdNumber);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(company);

        return MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(requestJson);
    }
}
