package com.example.foodblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@NoArgsConstructor // creates a default constructor with no arguments
@AllArgsConstructor // creates constructor with all parameters
@Data // creates getters and setters behind the scenes, no need to create them
@Entity // indicates that a class is an entity and should be mapped to a database table
@Table(name = "\"User\"") // overrides the postgres keyword restriction
public class User {

    @Id // next variable is a primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // strategy tells postgres how to auto increment the id
    private Integer id;
    private String username;
    private String email;
    private String password;
    @CreationTimestamp
    @Temporal(TemporalType.DATE) // sets the type of timestamp to a date
    private Date registrationDate;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Blog> blogs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false, unique = true) // specifies foreign key
    private Address address; // creates an address_id column on the Customer column, will always be unique when created

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    // SETTERS TO VALIDATE
    // Custom setter for username with validation
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        this.username = username;
    }

    // Custom setter for email with validation
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        } else if (!email.contains("@")) {
            throw new IllegalArgumentException("please enter a valid email address.");
        }
        this.email = email;
    }

    // Custom setter for password with validation
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;
    }
}