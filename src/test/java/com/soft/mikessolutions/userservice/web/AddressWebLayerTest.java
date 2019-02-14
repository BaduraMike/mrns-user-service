package com.soft.mikessolutions.userservice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.soft.mikessolutions.userservice.entities.Address;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AddressWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    //  TODO Change URL from hard-coded, source dependent way
    private final String URL = "/users/3/addresses/2";

    //  TODO Change Test from hard-coded, source dependent way
    @Test
    public void putShouldUpdateAddressByExistingIdForUserByExistingIdAndReturnHttpStatus204NoContent() throws Exception {
        MockHttpServletRequestBuilder builder = createJsonRequest(URL, "Al.Krakowczana", "666",
                "00-123", "Krasnystaw", "POLAND");

        this.mockMvc.perform(builder)
                .andExpect(status()
                        .isNoContent())
                .andDo(print());
    }

    //  TODO Change Test from hard-coded, source dependent way
    @Test
    public void putShouldNotUpdateAddressByExistingIdForUserByExistingIdAndReturnHttpStatus302Found() throws Exception {
        MockHttpServletRequestBuilder builder = createJsonRequest(URL, "Al. Krakowska", "110/114",
                "02-256", "Warsaw", "POLAND");

        this.mockMvc.perform(builder)
                .andExpect(status()
                        .isFound())
                .andDo(print());
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
