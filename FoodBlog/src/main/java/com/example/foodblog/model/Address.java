package com.example.foodblog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // creates a default constructor with no arguments
@AllArgsConstructor // creates constructor with all parameters
@Data // creates getters and setters behind the scenes, no need to create them
@Entity // indicates that a class is an entity and should be mapped to a database table
public class Address {

    @Id // next variable is a primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // strategy tells postgres how to auto increment the id
    private Integer id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    // ====== METHODS FOR VALIDATING DATA ========
    public void setCountry(String country) {
        // Check if the country is null or empty
        if (country == null || country.isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }

        // Check if the country has exactly two letters
        if (country.length() != 2) {
            throw new IllegalArgumentException("Country must be exactly two letters");
        }

        // Capitalize the country if it's not already capitalized
        this.country = country.toUpperCase();
    }

}