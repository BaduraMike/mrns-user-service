package com.soft.mikessolutions.userservice.services.serviceImpls;

import com.soft.mikessolutions.userservice.entities.User;
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
        Assert.assertFalse(userService.findAll().isEmpty());
    }

    @Test
    public void shouldFindUserByExistingId() {
        long id = userService.findAll().size();

        Assert.assertNotNull(userService.findById(id));
    }

    @Test
    public void shouldThrowUserNotFoundExceptionForNonExistingId() {
        //GIVEN
        //WHEN
        Long id = (long) (userService.findAll().size() + 1);
        //THEN
        expectedException.expect(UserNotFoundException.class);
        expectedException.expectMessage("User with {id} = " + id + " not found.");
        userService.findById(id);
    }

    @Test
    public void shouldSaveNewUser() {
        //GIVEN
        int preSaveUserCount = userService.findAll().size();
        User newUser = new User();
        //WHEN
        userService.save(newUser);
        int postSaveUserCount = userService.findAll().size();
        //THEN
        Assert.assertEquals(1, postSaveUserCount - preSaveUserCount);
    }


    @Test
    public void shouldDeleteUser() {
        //GIVEN
        User newUser = new User();
        userService.save(newUser);
        int preDeleteUserCount = userService.findAll().size();
        //WHEN
        userService.delete(newUser);
        int postDeleteUserCount = userService.findAll().size();
        //THEN
        Assert.assertEquals(1, preDeleteUserCount - postDeleteUserCount);
    }

    @Test
    public void shouldDeleteUserById1() {
        //GIVEN
        User newUser = new User();
        userService.save(newUser);
        int preDeleteUserCount = userService.findAll().size();
        //WHEN
        userService.deleteById(newUser.getId());
        int postDeleteUserCount = userService.findAll().size();
        //THEN
        Assert.assertEquals(1, preDeleteUserCount - postDeleteUserCount);
    }


}