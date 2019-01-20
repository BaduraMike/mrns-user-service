package com.soft.mikessolutions.userservice.web;

import com.soft.mikessolutions.userservice.services.UserService;
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
    public void getUserById1ShouldReturnHttpStatus302Found() throws Exception {
        this.mockMvc.perform(get("/users/1")).andDo(print()).andExpect(status().isFound());
    }

    @Test
    public void getUserById1ShouldReturnHttpStatus404NotFound() throws Exception {
        long id = userService.findAll().size() + 1;
        String pathToNonExistingUser = "/users/" + id;

        this.mockMvc.perform(get(pathToNonExistingUser)).andDo(print()).andExpect(status().isNotFound());
    }

}
