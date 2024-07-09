package com.example.notificationsservice.service;

import com.example.notificationsservice.model.Notification;
import com.example.notificationsservice.repository.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    INotificationRepository notificationRepository;

    public Notification addNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Integer id) throws Exception {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new Exception("Notification doesn't exist."));
    }

    public List<Notification> getNotificationsByUserId(Integer userId) {
        List<Notification> allNotificationsForUser = new ArrayList<>();

        for (Notification notification : notificationRepository.findAll()){
            if (notification.getRecipientId() == userId) {
                allNotificationsForUser.add(notification);
            }
        }

        return allNotificationsForUser;
    }

}
