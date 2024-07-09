package com.example.foodblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor // creates a default constructor with no arguments
@AllArgsConstructor // creates constructor with all parameters
@Data // creates getters and setters behind the scenes, no need to create them
@Builder
@Entity // indicates that a class is an entity and should be mapped to a database table
public class Tag {

    @Id // next variable is a primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // strategy tells postgres how to auto increment the id
    private Integer id;
    private String name;
    private String description;
    @JsonIgnore
    @ManyToMany(mappedBy = "tags") // maps to tags in Blog table
    private List<Blog> blogs = new ArrayList<>();

}