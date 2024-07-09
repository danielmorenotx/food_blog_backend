package com.example.foodblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor // creates a default constructor with no arguments
@AllArgsConstructor // creates constructor with all parameters
@Data // creates getters and setters behind the scenes, no need to create them
@Entity // indicates that a class is an entity and should be mapped to a database table
public class Blog {

    @Id // next variable is a primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // strategy tells postgres how to auto increment the id
    private Integer id;
    private String title;
    private String content;
    private Integer likes;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP) // sets the type of timestamp to a timestamp
    @Column(name = "creation_date")
    private Date creationDate;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified")
    private Date lastModified;

    @OneToMany(mappedBy = "blog") // one-to-many relationship with comments
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // one user per blog post

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    @JoinTable(
            name = "blog_tag",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags = new ArrayList<>();

    // ====== METHODS FOR VALIDATING DATA ========
    public void setTitle(String title) {
        // Limits to 100 characters
        if (title.length() > 100) {
            throw new IllegalArgumentException("Title cannot be over 100 characters");
        }

        this.title = title; // Set the title of the Blog object
    }

    public void setUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("A blog must be posted by an existing user");
        }

        this.user = user;
    }
}
