package com.soft.mikessolutions.userservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.soft.mikessolutions.userservice.entities.User;
import com.soft.mikessolutions.userservice.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    public void getAllUsersShouldReturnHttpStatus200Ok() throws Exception {
        this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getUserByExistingIdShouldReturnHttpStatus200Ok() throws Exception {
        long id = userService.findAll().size();
        String urlToExistingUser = "/users/" + id;

        this.mockMvc.perform(get(urlToExistingUser)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getUserByNonExistingIdShouldReturnHttpStatus404NotFound() throws Exception {
        long id = userService.findAll().size() + 1;
        String urlToNonExistingUser = "/users/" + id;

        this.mockMvc.perform(get(urlToNonExistingUser)).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void postUserShouldReturnHttpStatus201Created() throws Exception {
        String url = "/users";
        User user = new User();
        user.setFirstName("Jan");
        user.setLastName("Janowski");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = writer.writeValueAsString(user);

        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)).andDo(print()).andExpect(status().isCreated());
    }
}
