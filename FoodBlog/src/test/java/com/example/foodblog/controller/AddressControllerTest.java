package com.example.foodblog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.foodblog.model.Address;
import com.example.foodblog.service.AddressService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressController.class)
@AutoConfigureMockMvc
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressService addressService;

    private Address testAddress = new Address(1, "987 Fake Street", "Austin", "TX", "78795", "US"); // address to be used by all methods

    // ========== ADD ADDRESS ==========

    @Test
    public void testAddAddress() throws Exception {
        when(addressService.addAddress(any(Address.class))).thenReturn(testAddress);

        mockMvc.perform(post("/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAddress)))
                .andExpect(status().isOk());

        verify(addressService, times(1)).addAddress(testAddress);
    }

    // ========== GET ALL ADDRESSES ==========

    @Test
    public void testGetAllAddresses() throws Exception {
        List<Address> mockAddressList = new ArrayList<>();
        mockAddressList.add(new Address(1, "123 Main St", "Anytown", "ST", "12345", "US"));
        mockAddressList.add(new Address(2, "456 Oak St", "Othertown", "ST", "54321", "US"));

        when(addressService.getAllAddresses()).thenReturn(mockAddressList);

        mockMvc.perform(get("/addresses/all-addresses"))
                .andExpect(status().isOk());

        verify(addressService, times(1)).getAllAddresses();
    }

    // ========== GET ADDRESS BY ID ==========

    @Test
    public void testGetAddressById() throws Exception {
        when(addressService.getAddressById(anyInt())).thenReturn(testAddress);

        mockMvc.perform(get("/addresses/{id}", 1))
                .andExpect(status().isOk());

        verify(addressService, times(1)).getAddressById(1);
    }

    // ========== GET ADDRESS BY CITY ==========

    @Test
    public void testGetAddressByCity() throws Exception {
        when(addressService.getAddressByCity(anyString())).thenReturn(testAddress);

        mockMvc.perform(get("/addresses/by-city")
                        .param("city", "Anytown"))
                .andExpect(status().isOk());

        verify(addressService, times(1)).getAddressByCity("Anytown");
    }

    // ========== GET ADDRESS BY STATE ==========

    @Test
    public void testGetAddressByState() throws Exception {
        when(addressService.getAddressByState(anyString())).thenReturn(testAddress);

        mockMvc.perform(get("/addresses/by-state")
                        .param("state", "ST"))
                .andExpect(status().isOk());

        verify(addressService, times(1)).getAddressByState("ST");
    }

    // ========== UPDATE ADDRESS ==========

    @Test
    public void testUpdateAddress() throws Exception {
        Address mockAddressUpdates = new Address(1, "987 Real St", "Realcity", "RS", "98765", "US");

        when(addressService.updateAddress(anyInt(), any(Address.class))).thenReturn(mockAddressUpdates);

        mockMvc.perform(put("/addresses/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockAddressUpdates)))
                .andExpect(status().isOk());

        verify(addressService, times(1)).updateAddress(1, mockAddressUpdates);
    }

    // ========== DELETE ADDRESS ==========

    @Test
    public void testDeleteAddress() throws Exception {
        int addressToDelete = 1;

        doNothing().when(addressService).deleteAddress(addressToDelete);

        mockMvc.perform(delete("/addresses/{id}", addressToDelete))
                .andExpect(status().isOk());

        verify(addressService, times(1)).deleteAddress(addressToDelete);
    }
}
