package go.party.tcs.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import go.party.tcs.model.Notification;
import go.party.tcs.repository.NotificationRepository;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotification(String message, Integer userId) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDate(LocalDate.now()); 
        notification.setUserId(userId);
        notification.setVisto(false);
        notificationRepository.save(notification);
    }
}
