package com.example.foodblog.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {

    private String text;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP) // sets the type of timestamp to a timestamp
    private Date timestamp;
    private Integer likes;
    private Integer blogId;
    private Integer userId; // user who posted the comment

    public void setText(String text) {

        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment text cannot be empty");
        }
        this.text = text;
    }
}
