package com.example.foodblog.service;

import com.example.foodblog.model.Address;
import com.example.foodblog.respository.IAddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;
    @MockBean
    private IAddressRepository addressRepository;

    // ========== ADD ADDRESS =========
    @Test
    public void testAddAddress() {
        Address address = new Address(1,"1234 Test Street", "Austin", "TX", "78945", "US");

        // mock repository saving
        // when any address class is saved to the repo, return the mock address created
        when(addressRepository.save(any(Address.class))).thenReturn(address); // save method not called here, just giving instructions on what should happen

        // Add customer - this is where the .save method is actually called
        addressService.addAddress(address);

        // verifying that the save method was called once
        verify(addressRepository, times(1)).save(address);
    }

    // ========== GET ALL ADDRESSES =========
    @Test
    public void testGetAllAddresses() {
        // Create a list of mock addresses
        List<Address> addresses = Arrays.asList(new Address(), new Address(), new Address());

        // mock find all method
        when(addressRepository.findAll()).thenReturn(addresses);

        // this actually runs the method and assigns the result to allAddresses
        List<Address> allAddresses = addressService.getAllAddresses();

        // verifies that findAll() in addressRepository ran at least 1 time
        verify(addressRepository, times(1)).findAll();

        // asserts that the size of AllAddresses was 3 objects
        assertEquals(3, allAddresses.size());
    }

    // ========== GET ADDRESS BY ID =========
    @Test
    public void testGetAddressById() throws Exception {
        Address address = new Address(1,"1234 Test Street", "Austin", "TX", "78945", "US");

        // when findById(1) runs, it should return the address
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));

        // assigns the return address to a new Address variable
        Address addressOptional = addressService.getAddressById(1);

        // verifies that findById(1) ran twice
        verify(addressRepository, times(1)).findById(1);

        // asserts that the original address and the one returned by the getAddressById(1) method are the same
        assertEquals(address, addressOptional);
    }

    // ========== GET ADDRESS BY CITY =========
    @Test
    public void testGetAddressByCity() {
        Address address = new Address(1,"1234 Test Street", "Austin", "TX", "78945", "US");

        // when findById(1) runs, it should return the address
        when(addressRepository.findAddressByCity("Austin")).thenReturn(address);

        // assigns the return address to a new Address variable
        Address addressOptional = addressService.getAddressByCity("Austin");

        // verifies that findById(1) ran twice
        verify(addressRepository, times(1)).findAddressByCity("Austin");

        // asserts that the original address and the one returned by the getAddressById(1) method are the same
        assertEquals(address, addressOptional);
    }

    // ========== GET ADDRESS BY STATE =========
    @Test
    public void testGetAddressByState() {
        Address address = new Address(1,"1234 Test Street", "Austin", "TX", "78945", "US");

        // when findById(1) runs, it should return the address
        when(addressRepository.findAddressByState("TX")).thenReturn(address);

        // assigns the return address to a new Address variable
        Address addressOptional = addressService.getAddressByState("TX");

        // verifies that findById(1) ran twice
        verify(addressRepository, times(1)).findAddressByState("TX");

        // asserts that the original address and the one returned by the getAddressById(1) method are the same
        assertEquals(address, addressOptional);
    }

    // ========== UPDATE ADDRESS =========
    @Test
    public void testUpdateAddress() throws Exception {
        Address address = new Address(1,"1234 Test Street", "Austin", "TX", "78945", "US");
        Address updatedAddress = new Address(1,"54321 Test Street", "Houston", "TX", "78945", "US");

        // mock finding ID to update - mocks having the mock address in the DB already
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));
        // mock saving the new address information - when any class object is saved, return the updated address
        when(addressRepository.save(any(Address.class))).thenReturn(updatedAddress);

        // Call the actual updateAddress method
        Address result = addressService.updateAddress(1, updatedAddress);

        //Verify that the save method was called once
        verify(addressRepository, times(1)).save(updatedAddress);

        // assert that the updatedCustomer is the same as the result
        assertEquals(updatedAddress, result);
    }

    // ========== DELETE ADDRESS =========
    @Test
    public void testDeleteCustomer() throws Exception {
        Address address = new Address(1,"1234 Test Street", "Austin", "TX", "78945", "US");

        // mocking having this address in the DB
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));

        // nothing to return since we're deleting an object
        // Deleting the address from the mock DB
        addressService.deleteAddress(1);

        // verifying that the delete() method was called once
        verify(addressRepository, times(1)).delete(address);
    }
}
