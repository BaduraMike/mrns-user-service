package com.soft.mikessolutions.userservice.services.serviceImpls;

import com.soft.mikessolutions.userservice.exceptions.user.UserNotFoundException;
import com.soft.mikessolutions.userservice.services.UserService;
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
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldFindAllUsers() {
        Assert.assertEquals(3, userService.findAll().size());
    }

    @Test
    public void shouldFindUserById2() {
        Assert.assertEquals("grazyna.idzzesz@warbud.pl",userService.findById(2L).getEmail());
    }

    @Test
    public void shouldThrowUserNotFoundExceptionForId100() {
        //GIVEN
        //WHEN
        Long id = 100L;
        //THEN
        expectedException.expect(UserNotFoundException.class);
        expectedException.expectMessage("User with {id} = " + id + " not found.");
        userService.findById(id);
    }

}