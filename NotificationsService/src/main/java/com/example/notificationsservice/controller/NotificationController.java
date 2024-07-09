package com.example.notificationsservice.controller;

import com.example.notificationsservice.model.Notification;
import com.example.notificationsservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping
    public Notification addNotification(@RequestBody Notification notification) {
        return notificationService.addNotification(notification);
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("{id}")
    public Notification getNotificationById(@PathVariable Integer id) throws Exception {
        return notificationService.getNotificationById(id);
    }

    @GetMapping("/users/{userId}") // user who receives the notification
    public List<Notification> getNotificationsByUserId(@PathVariable Integer userId) {
        return notificationService.getNotificationsByUserId(userId);
    }
}
