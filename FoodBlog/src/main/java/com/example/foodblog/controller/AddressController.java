package com.example.foodblog.controller;

import com.example.foodblog.model.Address;
import com.example.foodblog.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses") // sets the first point to the API endpoint
public class AddressController {

    @Autowired
    AddressService addressService;

    // ========== CRUD OPERATIONS ==============
    // ----- add address -------
    @PostMapping
    public Address addAddress(@RequestBody Address address) {
        return addressService.addAddress(address);
    }

    // ----- get all addresses -------
    @GetMapping("/all-addresses")
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    // ----- get address by ID -----
    @GetMapping("/{id}")
    public Address getAddressById(@PathVariable Integer id) throws Exception {
        return addressService.getAddressById(id);
    }

    // ----- get address by city -----
    @GetMapping("/by-city")
    public Address getAddressByCity(@RequestParam String city) {
        return addressService.getAddressByCity(city);
    }

    // ----- get address by state -----
    @GetMapping("/by-state")
    public Address getAddressByState(@RequestParam String state) {
        return addressService.getAddressByState(state);
    }

    // ----- update address -----
    @PutMapping("/{id}")
    public Address updateAddress(@PathVariable Integer id, @RequestBody Address updatedAddressDetails) throws Exception {
        return addressService.updateAddress(id, updatedAddressDetails);
    }

    // ----- delete address -----
    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Integer id) throws Exception {
        addressService.deleteAddress(id);
    }
}