package com.example.foodblog.respository;

import com.example.foodblog.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Integer> {
    Address findAddressByCity(String city);

    Address findAddressByState(String state);
}
