package com.soft.mikessolutions.userservice.services.serviceImpls;

import com.soft.mikessolutions.userservice.entities.Address;
import com.soft.mikessolutions.userservice.exceptions.address.AddressAlreadyExistsException;
import com.soft.mikessolutions.userservice.exceptions.address.AddressNotFoundException;
import com.soft.mikessolutions.userservice.repositories.AddressRepository;
import com.soft.mikessolutions.userservice.services.AddressService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address findByAllParameters(String street, String streetNumber, String postCode,
                                    String city, String country) {
        return addressRepository
                .findByStreetAndStreetNumberAndPostCodeAndCityAndCountry(street, streetNumber, postCode, city, country);
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void delete(Address address) {
        addressRepository.delete(address);
    }

    @Override
    public void deleteById(Long id) {
        addressRepository.deleteById(findById(id).getId());
    }

    //query database by example if address with the given parameters exists:
    // if true -> throw AddressAlreadyExistsException
    // if false -> return nothing
    @Override
    public void checkExistence(String street, String streetNumber, String postCode, String city, String country) {
        Address address = new Address(street,streetNumber,postCode,city,country);
        Example<Address> addressExample = Example.of(address);

        if(addressRepository.exists(addressExample)){
            Address existingAddress = findByAllParameters(street, streetNumber, postCode, city ,country);
            throw new AddressAlreadyExistsException(existingAddress.getId());
        }
    }
}
