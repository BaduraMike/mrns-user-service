package com.soft.mikessolutions.userservice.services.serviceImpls;

import com.soft.mikessolutions.userservice.entities.Address;
import com.soft.mikessolutions.userservice.exceptions.address.AddressAlreadyExistsException;
import com.soft.mikessolutions.userservice.exceptions.address.AddressNotFoundException;
import com.soft.mikessolutions.userservice.services.AddressService;
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
public class AddressServiceImplTest {

    @Autowired
    private AddressService addressService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldFindAllAddresses() {
        Assert.assertFalse(addressService.findAll().isEmpty());
    }

    @Test
    public void shouldFindAddressByExistingId() {
        long id = addressService.findAll().size();

        Assert.assertNotNull(addressService.findById(id));
    }

    @Test
    public void shouldThrowAddressNotFoundExceptionForNonExistingId() {
        //GIVEN
        //WHEN
        Long id = (long) (addressService.findAll().size() + 1);
        //THEN
        expectedException.expect(AddressNotFoundException.class);
        expectedException.expectMessage("Address with {id} = " + id + " not found.");
        addressService.findById(id);
    }

    @Test
    public void shouldFindAddressByAllParameters() {
        long id = addressService.findAll().size() - 1;
        Address address1 = addressService.findById(id);
        Address address2 = addressService.findByAllParameters(address1.getStreet(), address1.getStreetNumber(),
                address1.getPostCode(), address1.getCity(), address1.getCountry());

        Assert.assertNotNull(address2);
    }

    @Test
    public void shouldNotFindAddressByAllParameters() {
        long id = addressService.findAll().size() - 1;
        String nonExistingStreet = "qwerty123456";
        Address address1 = addressService.findById(id);
        Address address2 = addressService.findByAllParameters(nonExistingStreet, address1.getStreetNumber(),
                address1.getPostCode(), address1.getCity(), address1.getCountry());

        Assert.assertNull(address2);
    }

    @Test
    public void shouldSaveNewAddress() {
        //GIVEN
        int preSaveAddressCount = addressService.findAll().size();
        Address newAddress = new Address();
        //WHEN
        addressService.save(newAddress);
        int postSaveAddressCount = addressService.findAll().size();
        //THEN
        Assert.assertEquals(1, postSaveAddressCount - preSaveAddressCount);
    }

    @Test
    public void shouldDeleteAddress() {
        //GIVEN
        Address newAddress = new Address();
        addressService.save(newAddress);
        int preDeleteAddressCount = addressService.findAll().size();
        //WHEN
        addressService.delete(newAddress);
        int postDeleteAddressCount = addressService.findAll().size();
        //THEN
        Assert.assertEquals(1, preDeleteAddressCount - postDeleteAddressCount);
    }

    @Test
    public void shouldDeleteUserByExistingId() {
        //GIVEN
        Address newAddress = new Address();
        addressService.save(newAddress);
        int preDeleteAddressCount = addressService.findAll().size();
        //WHEN
        addressService.deleteById(newAddress.getIdentityNumber());
        int postDeleteAddressCount = addressService.findAll().size();
        //THEN
        Assert.assertEquals(1, preDeleteAddressCount - postDeleteAddressCount);
    }

    @Test
    public void shouldThrowAddressAlreadyFoundExceptionForAddressWithExistingParameters() {
        //GIVEN
        //WHEN
        Long id = (long) (addressService.findAll().size());
        Address existingAddress = addressService.findById(id);
        //THEN
        expectedException.expect(AddressAlreadyExistsException.class);
        expectedException.expectMessage("Address with the given parameters already exists with {id} = " + id);
        addressService.checkExistence(existingAddress.getStreet(),existingAddress.getStreetNumber(),existingAddress.getPostCode(),
                existingAddress.getCity(), existingAddress.getCountry());
    }
}
