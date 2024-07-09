package com.example.foodblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@NoArgsConstructor // creates a default constructor with no arguments
@AllArgsConstructor // creates constructor with all parameters
@Builder
@Data // creates getters and setters behind the scenes, no need to create them
@Entity // indicates that a class is an entity and should be mapped to a database table
public class Comment {

    @Id // next variable is a primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // strategy tells postgres how to auto increment the id
    private Integer id;
    private String text;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP) // sets the type of timestamp to a timestamp
    private Date timestamp;
    private String commenterUsername;
    private Integer likes;

    @JsonIgnore
    @ManyToOne // many-to-one with blog
    @JoinColumn(name = "blog_id", nullable = false) // naming the foreign key as the blog ID
    private Blog blog;

    @JsonIgnore
    @ManyToOne // many comments can be written by one user
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // one user per comment

    public void setText(String text) {

        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment text cannot be empty");
        }
        this.text = text;
    }
}
