package com.example.notificationsservice.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private String text;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP) // sets the type of timestamp to a timestamp
    private Date timestamp;
    private Integer likes;
    private Integer blogId;
    private Integer userId; // user who posted the comment
}