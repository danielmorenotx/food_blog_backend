package com.example.foodblog.service;

import com.example.foodblog.model.Address;
import com.example.foodblog.respository.IAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    IAddressRepository addressRepository;

    // ========== CRUD OPERATIONS ==============
    // ----- add address -------
    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    // ----- get all addresses -------
    public List<Address> getAllAddresses() {
        return addressRepository.findAll(); // returns a list of address objects
    }

    // ----- get address by ID -----
    public Address getAddressById(Integer id) throws Exception {
        return addressRepository.findById(id)
                .orElseThrow(() -> new Exception("Address not found with ID " + id));
    }

    // ----- get address by city -----
    public Address getAddressByCity(String city) {
        return addressRepository.findAddressByCity(city);
    }

    // ----- get address by state -----
    public Address getAddressByState(String state) {
        return addressRepository.findAddressByState(state);
    }

    // ----- update address -----
    public Address updateAddress(Integer id, Address updatedAddressDetails) throws Exception {
        Address addressToUpdate = addressRepository.findById(id)
                .orElseThrow(() -> new Exception("Address not found with ID " + id));

        addressToUpdate.setStreet(updatedAddressDetails.getStreet());
        addressToUpdate.setCity(updatedAddressDetails.getCity());
        addressToUpdate.setState(updatedAddressDetails.getState());
        addressToUpdate.setZipCode(updatedAddressDetails.getZipCode());
        addressToUpdate.setCountry(updatedAddressDetails.getCountry());

        return addressRepository.save(addressToUpdate); // saves the updated address to the DB
    }

    // ----- delete address -----
    public void deleteAddress(Integer id) throws Exception {
        Address addressToDelete = addressRepository.findById(id)
                .orElseThrow(() -> new Exception("Address not found with ID " + id));

        addressRepository.delete(addressToDelete);
    }
}

