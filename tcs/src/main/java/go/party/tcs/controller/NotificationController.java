package go.party.tcs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import go.party.tcs.model.Notification;
import go.party.tcs.repository.NotificationRepository;
    

@Controller
public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/notificacoes")
    public String getUserNotifications() {
        //@PathVariable Long userId, Model model
        //List<Notification> notifications = notificationRepository.findByUserId(userId);
        //model.addAttribute("notifications", notifications);
        return "notificacoes"; // Nome do template Thymeleaf
    }
}

