package com.soft.mikessolutions.userservice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.soft.mikessolutions.userservice.entities.Address;
import com.soft.mikessolutions.userservice.entities.User;
import com.soft.mikessolutions.userservice.services.AddressService;
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
public class AddressWebLayerTest {
    private final String ADDRESS_BASE_URL = "/addresses";
    private final String USER_BASE_URL = "/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Test
    public void getAllAddressesShouldReturnHttpStatus200Ok() throws Exception {
        this.mockMvc.perform(get(ADDRESS_BASE_URL)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getAddressByExistingIdShouldReturnHttpStatus200Ok() throws Exception {
        long id = addressService.findAll().size();
        String urlToExistingAddress = ADDRESS_BASE_URL + "/" + id;

        this.mockMvc.perform(get(urlToExistingAddress)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getAddressByNonExistingIdShouldReturnHttpStatus404NotFound() throws Exception {
        long id = addressService.findAll().size() + 1;
        String urlToNonExistingAddress = ADDRESS_BASE_URL + "/" + id;

        this.mockMvc.perform(get(urlToNonExistingAddress)).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void postAddressShouldReturnHttpStatus201Created() throws Exception {
        Address address = new Address();
        address.setStreet("ul.Janowicka");
        address.setStreetNumber("09-090");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(address);

        this.mockMvc.perform(post(ADDRESS_BASE_URL).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)).andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void putAddressByExistingIdShouldUpdateAddressAndReturnHttpStatus204NoContent() throws Exception {
        long id = addressService.findAll().size();
        String existingAddressPutUrl = ADDRESS_BASE_URL + "/" + id;
        MockHttpServletRequestBuilder builder = createJsonRequest(existingAddressPutUrl, "ul.Janowicka",
                "09-090", null, null, null);

        this.mockMvc.perform(builder)
                .andExpect(status()
                        .isNoContent())
                .andDo(print());
    }

    @Test
    public void putAddressByNonExistingIdShouldReturnHttpStatus404NotFound() throws Exception {
        long id = addressService.findAll().size() + 1;
        String nonExistingAddressPutUrl = ADDRESS_BASE_URL + "/" + id;
        MockHttpServletRequestBuilder builder = createJsonRequest(nonExistingAddressPutUrl, "ul.Janowicka",
                "09-090", null, null, null);

        this.mockMvc.perform(builder)
                .andExpect(status()
                        .isNotFound())
                .andDo(print());
    }

    @Test
    public void putShouldUpdateAddressByExistingIdForUserByExistingIdAndReturnHttpStatus204NoContent() throws Exception {
        String URL = USER_BASE_URL + "/" + userService.findAll().size() + "/"
                + ADDRESS_BASE_URL + "/" + addressService.findAll().size();
        long existingAddressId = addressService.findAll().size();
        Address existingAddress = addressService.findById(existingAddressId);
        String randomStreetNumber = String.valueOf(Math.random() * 12345) + "XYZ";

        MockHttpServletRequestBuilder builder = createJsonRequest(URL, existingAddress.getStreet(),
                randomStreetNumber, existingAddress.getPostCode(), existingAddress.getCity(),
                existingAddress.getCountry());

        this.mockMvc.perform(builder)
                .andExpect(status()
                        .isNoContent())
                .andDo(print());
    }

    @Test
    public void putShouldNotUpdateAddressByExistingIdForUserByExistingIdAndReturnHttpStatus302Found() throws Exception {
        String URL = USER_BASE_URL + "/" + userService.findAll().size() + "/"
                + ADDRESS_BASE_URL + "/" + addressService.findAll().size();
        long existingAddressId = addressService.findAll().size();
        Address existingAddress = addressService.findById(existingAddressId);

        MockHttpServletRequestBuilder builder = createJsonRequest(URL, existingAddress.getStreet(),
                existingAddress.getStreetNumber(), existingAddress.getPostCode(), existingAddress.getCity(),
                existingAddress.getCountry());

        this.mockMvc.perform(builder)
                .andExpect(status()
                        .isFound())
                .andDo(print());
    }

    @Test
    public void deleteAddressByExistingIdShouldReturnHttpStatus204NoContent() throws Exception {
        long id = addressService.findAll().size();
        String urlToExistingAddress = ADDRESS_BASE_URL + "/" + id;

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(urlToExistingAddress)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteChildAddressByExistingIdShouldReturnHttpStatus400BadRequestForDataIntegrationViolation()
            throws Exception {
        User user = new User();
        Address address = new Address();
        addressService.save(address);
        user.setAddress(address);
        userService.save(user);

        long id = addressService.findAll().size();
        String urlToExistingChildCompany = ADDRESS_BASE_URL + "/" + id;

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(urlToExistingChildCompany)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private MockHttpServletRequestBuilder createJsonRequest(String URL, String street, String streetNumber, String postCode,
                                                            String city, String country) throws JsonProcessingException {
        Address address = new Address(street, streetNumber, postCode, city, country);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(address);

        return MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(requestJson);
    }
}
