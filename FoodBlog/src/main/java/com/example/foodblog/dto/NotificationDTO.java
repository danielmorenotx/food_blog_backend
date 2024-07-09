package com.example.foodblog.dto;
// talks between this monolith and the microservice

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO { // same in microservice
    private String text;
    private Integer recipientId;
    private Integer commentId;
    private String usernameOfCommenter;
}
