package com.example.notificationsservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String text;
    private Integer recipientId; // creator of the blog that was commented on, person receiving the notification
    private Integer commentId; // ID of the comment receiving the notification
    private String usernameOfCommenter; // user of the person who added the comment
}
